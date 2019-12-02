package by.androidacademy.firstapplication.androidservices

import android.app.Activity
import android.content.Intent
import android.os.Build


class ServiceDelegate {

    fun startDownloadIntentService(activity: Activity, isEnable: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val service = Intent(activity, DownloadJobIntentService::class.java)
            service.putExtra(SERVICE_INT_DATA, isEnable)
            DownloadJobIntentService.enqueueWork(activity, service)
        } else {
            val service = Intent(activity, DownloadIntentService::class.java)
            service.putExtra(SERVICE_INT_DATA, isEnable)
            activity.startService(service)
        }
    }

    fun startDownloadService(activity: Activity, isEnable: Boolean) {
        val service = Intent(activity, DownloadService::class.java)
        service.putExtra(SERVICE_INT_DATA, isEnable)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(service)
        } else {
            activity.startService(service)
        }
    }

    fun stopDownloadIntentService(activity: Activity) {
        val service = Intent(activity, DownloadIntentService::class.java)
        activity.stopService(service)
    }

    fun stopDownloadJobIntentService(activity: Activity) {
        val service = Intent(activity, DownloadJobIntentService::class.java)
        activity.stopService(service)
    }

    fun stopDownloadService(activity: Activity) {
        val service = Intent(activity, DownloadService::class.java)
        activity.stopService(service)
    }

    fun stopAllService(activity: Activity) {
        stopDownloadIntentService(activity)
        stopDownloadJobIntentService(activity)
        stopDownloadService(activity)
    }
}