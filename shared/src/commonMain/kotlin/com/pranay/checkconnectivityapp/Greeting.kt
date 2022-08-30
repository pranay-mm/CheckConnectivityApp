package com.pranay.checkconnectivityapp

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}