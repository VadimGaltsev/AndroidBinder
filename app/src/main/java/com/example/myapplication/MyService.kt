package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class RemoteService : Service() {

    private val random = Random(0)

    private val binder = object : IMyAidlInterface.Stub() {

        override fun getMagicNumber() = random.nextInt()
    }

    override fun onBind(intent: Intent?) = binder
}
