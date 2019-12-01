package by.androidacademy.firstapplication.androidservices

import android.app.IntentService
import android.app.Service
import android.content.Intent
import by.androidacademy.firstapplication.dependency.Dependencies


const val SERVICE_INT_DATA = "data_params"
private const val SERVICE_INTENT_PROGRESS = "IntentServiceProgress"

class DownloadIntentService : IntentService(SERVICE_INTENT_PROGRESS) {

    override fun onHandleIntent(intent: Intent?) {
        val params: Boolean? = intent?.getBooleanExtra(SERVICE_INT_DATA, false)
        params?.run {
            if (this) {
                Dependencies.run {
                    notificationsManager.showNotification()
                    heavyWorkManager.startWork()
                }
            } else {
                Dependencies.run {
                    notificationsManager.hideNotification()
                    heavyWorkManager.resetProgress()
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_NOT_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        Dependencies.run {
            heavyWorkManager.run {
                resetProgress()
                onDestroy()
            }
            notificationsManager.hideNotification()
        }
    }
}