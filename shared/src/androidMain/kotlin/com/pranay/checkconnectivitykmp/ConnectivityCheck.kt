package com.pranay.checkconnectivitykmp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual class ConnectivityCheck(private val context: Context) {
    private val isNetworkConnected = MutableStateFlow(false)

    private var connectivityManager: ConnectivityManager? = null
    actual fun startListener(onConnectionStatus: (Boolean) -> Unit){
        initCallBack(onConnectionStatus)
        try {
            if (connectivityManager == null) {
                connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager?.apply {
                    registerDefaultNetworkCallback(networkCallback)
                    isNetworkConnected.value=activeNetwork!=null
                }
            } else {
                // API 23 and below
                val networkRequest = NetworkRequest.Builder().apply {
                    addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    }
                }.build()

                connectivityManager?.apply {
                    registerNetworkCallback(networkRequest, networkCallback)
                    @Suppress("DEPRECATION")
                    val currentNetwork = connectivityManager?.activeNetworkInfo
                    isNetworkConnected.value = currentNetwork == null || (
                            currentNetwork.type != ConnectivityManager.TYPE_ETHERNET &&
                                    currentNetwork.type != ConnectivityManager.TYPE_WIFI &&
                                    currentNetwork.type != ConnectivityManager.TYPE_MOBILE
                            )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            isNetworkConnected.value = false
        }
    }
    actual fun stopListener(){
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }
    private fun initCallBack(onConnectionStatus: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            isNetworkConnected.collect { status ->
                withContext(Dispatchers.Main) {
                    onConnectionStatus(status)
                }
            }
        }
    }
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            isNetworkConnected.value = true
        }

        override fun onLost(network: Network) {
            isNetworkConnected.value = false
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            isNetworkConnected.value =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED) &&
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                        } else { true }

        }

    }

}