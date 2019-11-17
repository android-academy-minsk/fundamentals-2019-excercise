package by.androidacademy.firstapplication.dependency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//note, pls, in a real project do via DI
object AndroidServiceDelegate {

    private val progressUpdaterService: MutableLiveData<Int> = MutableLiveData()
    private val progressUpdaterIntentService: MutableLiveData<Int> = MutableLiveData()

    fun setProgressToService(value: Int) {
        progressUpdaterService.postValue(value)
    }

    fun setProgressToIntentService(value: Int) {
        progressUpdaterIntentService.postValue(value)
    }

    fun subscribeToServiceProgress(): LiveData<Int> {
        return progressUpdaterService
    }

    fun subscribeToIntentServiceProgress(): LiveData<Int> {
        return progressUpdaterIntentService
    }

}