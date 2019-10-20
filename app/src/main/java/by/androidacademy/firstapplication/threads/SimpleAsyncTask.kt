package by.androidacademy.firstapplication.threads

import android.os.Handler
import android.os.Looper
import android.os.SystemClock

class SimpleAsyncTask(private val listener: TaskEventContract.Lifecycle) {

    @Volatile
    var isCancelled = false
        private set
    private var mBackgroundThread: Thread? = null

    /**
     * Runs on the UI thread before [.doInBackground].
     */
    private fun onPreExecute() {
        listener.onPreExecute()
    }

    /**
     * Runs on new thread after [.onPreExecute] and before [.onPostExecute].
     */
    private fun doInBackground() {
        val end = 10
        for (i in 0..end) {
            if (isCancelled) {
                return
            }

            publishProgress(i)
            SystemClock.sleep(500)
        }
    }

    /**
     * Runs on the UI thread after [.doInBackground]
     */
    private fun onPostExecute() {
        listener.onPostExecute()
    }

    private fun onProgressUpdate(progress: Int) {
        listener.onProgressUpdate(progress)
    }

    fun execute() {
        runOnUiThread(Runnable {
            onPreExecute()
            mBackgroundThread = object : Thread("Handler_executor_thread") {
                override fun run() {
                    doInBackground()
                    runOnUiThread(Runnable { onPostExecute() })
                }
            }
            mBackgroundThread!!.start()
        })
    }

    private fun runOnUiThread(runnable: Runnable) {
        Handler(Looper.getMainLooper()).post(runnable)
    }

    private fun publishProgress(progress: Int) {
        runOnUiThread(Runnable { onProgressUpdate(progress) })
    }

    fun cancel() {
        isCancelled = true
        if (mBackgroundThread != null) {
            mBackgroundThread!!.interrupt()
        }
    }
}
