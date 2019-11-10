package by.androidacademy.firstapplication.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import java.util.*

class ServiceProgress: Service() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private val maxProgress: Int = 100

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        // Do a periodic task
        mHandler = Handler()
        mRunnable = Runnable { showProgressNumber() }
        mHandler.postDelayed(mRunnable, 1000)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }

    // Custom method to do a task
    private fun showProgressNumber() {
        val
        val number = rand.nextInt(100)
        mHandler.postDelayed(mRunnable, 1000)
    }
}