package by.androidacademy.firstapplication.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkerManager(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            // Do something
            Result.success()
        } catch (error: Throwable) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

}