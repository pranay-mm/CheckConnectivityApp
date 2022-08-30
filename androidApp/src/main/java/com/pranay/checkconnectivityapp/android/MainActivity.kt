package com.pranay.checkconnectivityapp.android

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pranay.checkconnectivitykmp.Greeting
import android.widget.TextView
import com.pranay.checkconnectivitykmp.ConnectivityCheck

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    private var connectivityCheck: ConnectivityCheck?=null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
        connectivityCheck= ConnectivityCheck(this)
        connectivityCheck?.startListener {
            tv.text = "You are " + if(it) "Connected" else "DisConnected"
        }
    }

    override fun onPause() {
        super.onPause()
        connectivityCheck?.stopListener()
    }
}
