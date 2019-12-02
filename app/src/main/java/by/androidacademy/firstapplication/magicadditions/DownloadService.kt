package by.androidacademy.firstapplication.magicadditions

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.magicadditions.DownloadThread.DownloadCallBack

class DownloadService : Service() {

    private lateinit var notificationManager: NotificationManagerCompat

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("DownloadService", "#onStartCommand")
        notificationManager = NotificationManagerCompat.from(applicationContext)
        startForeground()
        val posterUrl = intent.getStringExtra(POSTER_URL)
        if (posterUrl == null) {
            Log.e("DownloadService", "Required arguments were not provided")
            return START_NOT_STICKY
        }
        Log.d("DownloadService", "URL: $posterUrl")
        startDownload(posterUrl)
        return START_STICKY
    }

    private fun startDownload(posterUrl: String) {
        // Download with any appropriate way. Using thread directly is just another example.
        DownloadThread(posterUrl, object : DownloadCallBack {
            override fun onProgressUpdate(percent: Int) {
                Log.d("DownloadService", "#onProgressUpdate: $percent%")
                updateNotification(percent)
            }

            override fun onDownloadFinished(filePath: String) {
                Log.d("DownloadService", "#onDownloadFinished: $filePath")
                sendBroadcastMsgDownloadComplete(filePath)
                stopSelf()
            }

            override fun onError(error: String) {
                Log.e("DownloadService", "Error: $error")
                notificationManager.notify(ERROR_NOTIFICATION_ID, createErrorNotification())
                notificationManager.cancel(ONGOING_NOTIFICATION_ID)
                stopSelf()
            }
        }).start()
    }

    private fun startForeground() {
        createNotificationChannel()
        val notification = createNotification(0)
        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }

    private fun createNotification(progress: Int): Notification {
        val notificationIntent = Intent(this, PosterActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        return NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle(getString(R.string.notification_title, progress))
            .setContentText(getText(R.string.notification_message))
            .setSmallIcon(R.drawable.ic_stat_download)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createErrorNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle(getText(R.string.notification_error_title))
            .setContentText(getText(R.string.notification_error_message))
            .setSmallIcon(R.drawable.ic_stat_download)
            .build()
    }

    private fun updateNotification(progress: Int) {
        val notification = createNotification(progress)
        notificationManager.notify(ONGOING_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // The user-visible name of the channel.
            val name: CharSequence = getString(R.string.channel_name)
            // The user-visible description of the channel.
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(
                CHANNEL_DEFAULT_IMPORTANCE,
                name,
                importance
            )
            // Configure the notification channel.
            mChannel.description = description
            mChannel.enableLights(true)
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    private fun sendBroadcastMsgDownloadComplete(posterPath: String) {
        val intent: Intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent(this, CompleteReceiver::class.java)
        } else {
            Intent(ACTION_DOWNLOAD_COMPLETE)
        }
        intent.putExtra(POSTER_PATH, posterPath)
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DownloadService", "#onDestroy")
    }

    override fun onBind(intent: Intent): IBinder? = null

    companion object {

        const val ACTION_DOWNLOAD_COMPLETE = "by.androidacademy.firstapplication.magicadditions.DOWNLOAD_COMPLETE"
        const val POSTER_PATH = "POSTER_PATH"

        private const val POSTER_URL = "POSTER_URL"
        private const val ONGOING_NOTIFICATION_ID = 987
        private const val ERROR_NOTIFICATION_ID = 1024
        private const val CHANNEL_DEFAULT_IMPORTANCE = "01_Channel"

        fun startService(context: Context, posterUrl: String) {
            val intent = Intent(context, DownloadService::class.java)
                .putExtra(POSTER_URL, posterUrl)
            context.startService(intent)
        }
    }
}