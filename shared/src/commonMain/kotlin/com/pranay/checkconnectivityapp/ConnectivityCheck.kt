package com.pranay.checkconnectivityapp

expect class ConnectivityCheck {
    fun stopListener()
    fun startListener(onConnectionStatus: (Boolean) -> Unit)
}