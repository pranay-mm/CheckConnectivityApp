package com.pranay.checkconnectivitykmp

expect class ConnectivityCheck {
    fun stopListener()
    fun startListener(onConnectionStatus: (Boolean) -> Unit)
}