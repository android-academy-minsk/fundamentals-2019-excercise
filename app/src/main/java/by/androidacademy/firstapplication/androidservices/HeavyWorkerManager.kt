package by.androidacademy.firstapplication.androidservices

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


const val DELAY_TICK = 100L
const val MAX_PROGRESS = 100
const val DELAY_VALUE = DELAY_TICK * MAX_PROGRESS + 1000L//-1sec for cold start

class HeavyWorkerManager {

    private var handler: Handler = Handler()
    private var runnable: Runnable? = null
    private var counter = 0
    private val progressUpdaterService: MutableLiveData<Int> = MutableLiveData()

    init {
        initWork()
    }

    fun startWork() {
        runnable?.run { handler.post(this) }
    }

    fun resetProgress() {
        counter = 0
        showProgressNumber(counter)
    }

    fun finishedWork() {
        counter = 100
        showProgressNumber(counter)
    }

    fun onDestroy() {
        runnable?.run {
            handler.removeCallbacks(this)
        }
    }

    fun getProgressUpdaterService(): LiveData<Int> = progressUpdaterService

    private fun initWork() {
        runnable = Runnable {
            showProgressNumber(
                when {
                    counter < MAX_PROGRESS -> {
                        counter++
                    }
                    else -> {
                        counter
                    }
                }
            )
            runnable?.run {
                handler.postDelayed(this, DELAY_TICK)
            }
        }
    }

    private fun showProgressNumber(progress: Int) {
        progressUpdaterService.postValue(progress)
    }
}