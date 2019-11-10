package by.androidacademy.firstapplication.services

import android.os.Handler
import android.os.Looper
import android.os.Message

class ServiceHandler(looper: Looper) : Handler(looper) {
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        for (i in 0..100) {

        }

    }
}