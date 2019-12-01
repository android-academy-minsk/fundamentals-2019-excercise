package by.androidacademy.firstapplication.androidservices.viewmodel

import androidx.lifecycle.ViewModel
import by.androidacademy.firstapplication.androidservices.HeavyWorkManager
import by.androidacademy.firstapplication.androidservices.WorkerParamsRequest

class ServiceViewModel(
    private val heavyWorkManager: HeavyWorkManager,
    private val workerParamsRequest: WorkerParamsRequest
) : ViewModel() {

    fun getWorker() = workerParamsRequest
    fun getProgress() = heavyWorkManager.getProgressUpdaterService()

    fun cancelWork() {
        workerParamsRequest.cancelWork()
    }
}