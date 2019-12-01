package by.androidacademy.firstapplication.androidservices

import android.app.IntentService
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