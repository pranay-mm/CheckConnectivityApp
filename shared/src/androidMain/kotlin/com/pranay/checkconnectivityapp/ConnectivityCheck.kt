package com.pranay.checkconnectivityapp

actual class ConnectivityCheck {
    actual fun startListener(onConnectionStatus: (Boolean) -> Unit){

    }
    actual fun stopListener(){

    }
}