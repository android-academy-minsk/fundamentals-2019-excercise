package by.androidacademy.firstapplication.threads

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private const val LOG_TAG = "CoroutineTask"
private const val MAX_COUNTER_VALUE = 10
private const val DELAY_IN_MILLS = 500L

class CoroutineTask(private val listener: TaskEventContract.Lifecycle) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob()

    private var newJob: Job? = null

    fun createTask() {
        newJob = launch(context = Dispatchers.IO, start = CoroutineStart.LAZY) {

            Log.d(LOG_TAG, "Start job on IO thread | thread: ${Thread.currentThread().name}")

            repeat(MAX_COUNTER_VALUE) { counter ->
                Log.d(
                    LOG_TAG,
                    "New counter value [counter: $counter] | thread: ${Thread.currentThread().name}"
                )

                launch(Dispatchers.Main) {
                    Log.d(
                        LOG_TAG,
                        "Switch thread to main [counter: $counter] | thread: ${Thread.currentThread().name}"
                    )

                    listener.onProgressUpdate(counter)
                }

                delay(DELAY_IN_MILLS)
            }

            launch(Dispatchers.Main) {
                Log.d(
                    LOG_TAG,
                    "Switch thread to main again | thread: ${Thread.currentThread().name}"
                )

                listener.onPostExecute()
            }
        }

        listener.onPreExecute()
    }

    fun cancel() {
        Log.d(LOG_TAG, "Before 'cancel' of job | thread: ${Thread.currentThread().name}")

        newJob?.cancel()
        coroutineContext.cancel()

        Log.d(LOG_TAG, "Before 'cancel' of job | thread: ${Thread.currentThread().name}")
    }

    fun start(): Boolean? {
        Log.d(LOG_TAG, "Before 'start' of job | thread: ${Thread.currentThread().name}")
        val started = newJob?.start()
        Log.d(
            LOG_TAG,
            "After 'start' of job (started: $started) | thread: ${Thread.currentThread().name}"
        )

        return started
    }
}