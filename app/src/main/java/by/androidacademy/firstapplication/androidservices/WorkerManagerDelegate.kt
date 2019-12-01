package by.androidacademy.firstapplication.androidservices

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import by.androidacademy.firstapplication.dependency.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkerManagerDelegate(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val heavyWorkManager: HeavyWorkManager = Dependencies.heavyWorkManager

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            heavyWorkManager.startWork()
            // Do something
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

}