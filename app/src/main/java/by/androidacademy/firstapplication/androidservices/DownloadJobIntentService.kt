package by.androidacademy.firstapplication.androidservices

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.app.JobIntentService
import by.androidacademy.firstapplication.dependency.Dependencies


const val JOB_ID = 10

class DownloadJobIntentService : JobIntentService() {

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, DownloadJobIntentService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        val params: Boolean? = intent.getBooleanExtra(SERVICE_INT_DATA, false)

        Handler(Looper.getMainLooper()).post {
            params?.run { startWork(this) }
        }
        Thread.sleep(DELAY_VALUE)//note, this is a random number, for an example hard work
    }

    private fun startWork(isEnable: Boolean) {
        isEnable.run {
            when {
                this -> {
                    Dependencies.run {
                        notificationsManager.showNotification()
                        heavyWorkManager.startWork()
                    }
                }
                else -> resetWork()
            }
        }
    }

    private fun resetWork() {
        Handler(Looper.getMainLooper()).post {
            Dependencies.run {
                heavyWorkManager.run {
                    finishedWork()
                    onDestroy()

                }
                notificationsManager.hideNotification()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        resetWork()
    }

}