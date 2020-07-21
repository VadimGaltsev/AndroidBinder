package com.example.myapplication

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val serviceStatusLabel by lazy { findViewById<TextView>(R.id.service_status) }
    private val answerButton by lazy { findViewById<Button>(R.id.get_answer) }
    private val answerText by lazy { findViewById<TextView>(R.id.magic_answer) }
    private var iRemoteService: IMyAidlInterface? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceStatusLabel.text = "Connected"
            iRemoteService = IMyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceStatusLabel.text = "Disconnected"
            iRemoteService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Intent(this, RemoteService::class.java)
            .apply {
                bindService(this, serviceConnection, Service.BIND_AUTO_CREATE)
            }
        answerButton.setOnClickListener {
            answerText.text = iRemoteService?.magicNumber.toString()
        }
    }
}
