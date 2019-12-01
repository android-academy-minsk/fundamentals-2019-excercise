package by.androidacademy.firstapplication.androidservices

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HeavyWorkManager {

    private val mHandler: Handler = Handler()
    private lateinit var mRunnable: Runnable
    private val maxProgress: Int = 100
    private var counter = 0

    private val progressUpdaterService: MutableLiveData<Int> = MutableLiveData()

    fun startWork() {

        mRunnable = Runnable {
            showProgressNumber(
                if (counter <= maxProgress) {
                    counter++
                } else {
                    counter
                }
            )
        }
        mHandler.postDelayed(mRunnable, 1000)
    }

    fun resetProgress() {
        counter = 0
    }

    fun onDestroy() {
        mHandler.removeCallbacks(mRunnable)
    }

    fun getProgressUpdaterService(): LiveData<Int> = progressUpdaterService

    private fun showProgressNumber(progress: Int) {
        progressUpdaterService.postValue(progress)
    }
}