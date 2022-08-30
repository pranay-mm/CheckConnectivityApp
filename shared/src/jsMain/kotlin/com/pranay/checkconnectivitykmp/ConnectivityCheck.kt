package com.pranay.checkconnectivitykmp

import isOnline
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch

@JsName("checkConnection")
val connectivityCheck = ConnectivityCheck()


actual class ConnectivityCheck {

    @JsName("startListener")
    actual fun startListener(onConnectionStatus: (Boolean) -> Unit) {
        GlobalScope.launch {
            onConnectionStatus(isOnline().await())
        }

    }

    @JsName("stopListener")
    actual fun stopListener() {
        //no need to do any code here for now
    }

}