package by.androidacademy.firstapplication.androidservices

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HeavyWorkManager {

    private var handler: Handler? = null
    private lateinit var runnable: Runnable
    private val maxProgress: Int = 100
    private var counter = 0

    private val progressUpdaterService: MutableLiveData<Int> = MutableLiveData()

    fun startWork() {
        handler = Handler()
        runnable = Runnable {
            showProgressNumber(
                if (counter <= maxProgress) {
                    counter++
                } else {
                    counter
                }
            )
        }
        handler?.postDelayed(runnable, 1000)
    }

    fun resetProgress() {
        counter = 0
    }

    fun onDestroy() {
        handler?.removeCallbacks(runnable)
    }

    fun getProgressUpdaterService(): LiveData<Int> = progressUpdaterService

    private fun showProgressNumber(progress: Int) {
        progressUpdaterService.postValue(progress)
    }
}