package by.androidacademy.firstapplication.androidservices.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ServiceViewModelState {

    private val progressData: MutableLiveData<Int> = MutableLiveData()
    private val isEnableButton: MutableLiveData<Boolean> = MutableLiveData()
    private val isEnableService: MutableLiveData<Boolean> = MutableLiveData()
    private val isEnableIntentService: MutableLiveData<Boolean> = MutableLiveData()
    private val isEnableJobIntentService: MutableLiveData<Boolean> = MutableLiveData()

    fun downloadProgress(): LiveData<Int> = progressData

    fun isButtonsEnable(): LiveData<Boolean> = isEnableButton

    fun isEnableDownloadService(): LiveData<Boolean> = isEnableService

    fun isEnableDownloadIntentService(): LiveData<Boolean> = isEnableIntentService

    fun isEnableDownloadJobIntentService(): LiveData<Boolean> = isEnableJobIntentService

    fun setProgress(progressData: Int) = this.progressData.postValue(progressData)

    fun isEnableButton(isEnableButton: Boolean) = this.isEnableButton.postValue(isEnableButton)

    fun isEnableService(isEnableDownloadService: Boolean) =
        this.isEnableService.postValue(isEnableDownloadService)

    fun isEnableIntentService(isEnableDownloadIntentService: Boolean) =
        this.isEnableIntentService.postValue(isEnableDownloadIntentService)

    fun isEnableJobIntentService(isEnableDownloadJobIntentService: Boolean) =
        this.isEnableJobIntentService.postValue(isEnableDownloadJobIntentService)

}