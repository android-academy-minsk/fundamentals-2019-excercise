package by.androidacademy.firstapplication.androidservices

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import by.androidacademy.firstapplication.dependency.Dependencies
import by.androidacademy.firstapplication.utils.NotificationsManager

class WorkerManagerDelegate(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    private val notificationsManager: NotificationsManager = Dependencies.notificationsManager
    private val heavyWorkManager: HeavyWorkerManager = Dependencies.heavyWorkManager

    override fun doWork(): Result {
        return try {
            notificationsManager.showNotification()
            heavyWorkManager.startWork()
            Thread.sleep(DELAY_VALUE)
            Result.success()
        } catch (error: Throwable) {
            heavyWorkManager.resetProgress()
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    override fun onStopped() {
        super.onStopped()
        notificationsManager.hideNotification()
    }

}