package by.androidacademy.firstapplication.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import by.androidacademy.firstapplication.dependency.AndroidServiceDelegate

class ServiceProgress : Service() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private val maxProgress: Int = 100

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        // Do a periodic task
        mHandler = Handler()
        var counter = 0
        mRunnable = Runnable {
            showProgressNumber(
                if (counter < maxProgress) {
                    counter++
                } else {
                    counter
                }
            )
        }
        mHandler.postDelayed(mRunnable, 1000)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }

    // Custom method to do a task
    private fun showProgressNumber(progress: Int) {
        AndroidServiceDelegate.setProgressToService(progress)//to do inject
        mHandler.postDelayed(mRunnable, 1000)
    }
}