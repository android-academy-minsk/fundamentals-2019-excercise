package by.androidacademy.firstapplication.androidservices

import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import by.androidacademy.firstapplication.App
import java.util.*

class WorkerParamsRequest {

    private var idWork: UUID = UUID.randomUUID()
    private val constraints = Constraints.Builder().build()
    private val worker: WorkManager = WorkManager.getInstance(App.instance)

    fun enqueue() {
        worker.enqueue(uploadWorkRequest())
    }

    fun workManagerInfo(): LiveData<WorkInfo> {
        return worker.getWorkInfoByIdLiveData(idWork)
    }

    fun cancelWork() {
        worker.cancelWorkById(idWork)
    }

    private fun uploadWorkRequest() = OneTimeWorkRequestBuilder<WorkerManagerDelegate>()
        .setConstraints(constraints)
        .build().run {
            idWork = id
            this
        }

}