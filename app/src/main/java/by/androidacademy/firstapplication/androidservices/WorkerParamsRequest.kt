package by.androidacademy.firstapplication.androidservices

import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import by.androidacademy.firstapplication.App
import by.androidacademy.firstapplication.dependency.Dependencies
import java.util.concurrent.TimeUnit

class WorkerParamsRequest {

    private val constraints = Constraints.Builder()
        .build()

    private val uploadWorkRequest = OneTimeWorkRequestBuilder<WorkerManagerDelegate>()
        .setConstraints(constraints)
        .setInitialDelay(5, TimeUnit.SECONDS)
        .build()

    private val worker: WorkManager = WorkManager.getInstance(App.instance)

    fun enqueue() {
        worker
            .enqueue(Dependencies.workerParamsRequest.uploadWorkRequest)
    }

    fun workManagerInfo(): LiveData<WorkInfo> {
        return worker.getWorkInfoByIdLiveData(uploadWorkRequest.id)
    }

    fun cancelWork() {
        worker.cancelWorkById(uploadWorkRequest.id)
    }

}