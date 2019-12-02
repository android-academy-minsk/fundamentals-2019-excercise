package by.androidacademy.firstapplication.androidservices.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.androidacademy.firstapplication.androidservices.HeavyWorkerManager
import by.androidacademy.firstapplication.androidservices.MAX_PROGRESS
import by.androidacademy.firstapplication.androidservices.WorkerParamsRequest

class ServiceViewModel(
    private val heavyWorkManager: HeavyWorkerManager,
    private val workerParamsRequest: WorkerParamsRequest
) : ViewModel() {

    private val progressData: MutableLiveData<Int> = MutableLiveData()
    private val isEnableButton: MutableLiveData<Boolean> = MutableLiveData()
    private val isEnableDownloadService: MutableLiveData<Boolean> = MutableLiveData()
    private val isEnableDownloadIntentService: MutableLiveData<Boolean> = MutableLiveData()
    private val isEnableDownloadJobIntentService: MutableLiveData<Boolean> = MutableLiveData()

    private val progressStatus = Observer<Int> { progress ->
        when (progress) {
            MAX_PROGRESS -> resetState()
            else -> progressData.postValue(progress)
        }
    }

    init {
        subscribeToDownloadProgress()
    }

    fun getWorker() = workerParamsRequest

    fun downloadProgress(): LiveData<Int> = progressData

    fun isButtonsEnable(): LiveData<Boolean> = isEnableButton

    fun isEnableDownloadService(): LiveData<Boolean> = isEnableDownloadService

    fun isEnableDownloadIntentService(): LiveData<Boolean> = isEnableDownloadIntentService

    fun isEnableDownloadJobIntentService(): LiveData<Boolean> = isEnableDownloadJobIntentService

    override fun onCleared() {
        super.onCleared()
        heavyWorkManager.run {
            resetProgress()
            onDestroy()
        }
        cancelWork()
        unsubscribeDownloadProgress()
    }

    private fun cancelWork() = workerParamsRequest.cancelWork()

    private fun resetState() {
        isEnableButton.postValue(true)
        isEnableDownloadService.postValue(false)
        isEnableDownloadIntentService.postValue(false)
        isEnableDownloadJobIntentService.postValue(false)
        cancelWork()
        heavyWorkManager.run {
            resetProgress()
            onDestroy()
        }
    }


    private fun subscribeToDownloadProgress() {
        heavyWorkManager.getProgressUpdaterService().observeForever(progressStatus)
    }

    private fun unsubscribeDownloadProgress() {
        heavyWorkManager.getProgressUpdaterService().removeObserver(progressStatus)
    }

}