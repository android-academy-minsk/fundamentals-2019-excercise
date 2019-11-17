package by.androidacademy.firstapplication.services

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import by.androidacademy.firstapplication.dependency.AndroidServiceDelegate

const val SERVICE_INTENT_PROGRESS = "IntentServiceProgress"

class IntentServiceProgress : IntentService(SERVICE_INTENT_PROGRESS) {


    override fun onHandleIntent(p0: Intent?) {

    }

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private val maxProgress: Int = 100

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
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

    private fun showProgressNumber(progress: Int) {
        AndroidServiceDelegate.setProgressToIntentService(progress)
        mHandler.postDelayed(mRunnable, 1000)
    }
}