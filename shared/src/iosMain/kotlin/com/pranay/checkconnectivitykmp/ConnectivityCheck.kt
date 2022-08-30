package com.pranay.checkconnectivitykmp

import cocoapods.Reachability.Reachability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class ConnectivityCheck {
    private val networkStatus = MutableStateFlow(false)
    private var reachability: Reachability? = Reachability.reachabilityForInternetConnection()

    actual fun startListener(onConnectionStatus: (Boolean) -> Unit){
        initCallBack(onConnectionStatus)
        dispatch_async(dispatch_get_main_queue()) {
            reachability?.apply {
                reachableBlock = {
                    networkStatus.value=true
                }
                unreachableBlock = {
                    networkStatus.value=false
                }
                startNotifier()
                networkStatus.value = isReachable()
            }
        }
    }
    actual fun stopListener(){
        reachability?.stopNotifier()
    }

    private fun initCallBack(onConnectionStatus: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            networkStatus.collect { status ->
                withContext(Dispatchers.Main) {
                    onConnectionStatus(status)
                }
            }
        }
    }
}