package by.androidacademy.firstapplication.androidservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import by.androidacademy.firstapplication.dependency.Dependencies

class DownloadService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val params: Boolean? = intent.getBooleanExtra(SERVICE_INT_DATA, false)
        params?.run {
            if (this && startId == 1) {
                Dependencies.run {
                    startForeground(1, notificationsManager.notificationBuilder.build())
                    heavyWorkManager.startWork()
                }
            }
        }
        return START_NOT_STICKY
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