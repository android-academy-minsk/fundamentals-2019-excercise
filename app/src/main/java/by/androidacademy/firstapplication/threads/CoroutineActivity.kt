package by.androidacademy.firstapplication.threads

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.androidacademy.firstapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private const val LOG_TAG = "CoroutineActivity"
private const val MAX_COUNTER_VALUE = 10
private const val DELAY_IN_MILLS = 500L

class CoroutineActivity : AppCompatActivity(), CoroutineScope, TaskEventsListener {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob()

    private var newJob: Job? = null

    private var coroutineFragment: CounterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val fragment = CounterFragment.newInstance(
                getString(R.string.fragment_coroutine_title)
            ).also { coroutineFragment = it }

            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        newJob?.cancel()
        coroutineContext.cancel()
    }

    override fun createTask() {
        Toast.makeText(this, getString(R.string.msg_oncreate), Toast.LENGTH_SHORT).show()

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

                    onProgressUpdate(counter)
                }

                delay(DELAY_IN_MILLS)
            }

            launch(Dispatchers.Main) {
                Log.d(
                    LOG_TAG,
                    "Switch thread to main again | thread: ${Thread.currentThread().name}"
                )

                onPostExecute()
            }
        }

        onPreExecute()
    }

    override fun startTask() {
        Log.d(LOG_TAG, "Before 'start' of job | thread: ${Thread.currentThread().name}")
        val started = newJob?.start()
        Log.d(
            LOG_TAG,
            "After 'start' of job (started: $started) | thread: ${Thread.currentThread().name}"
        )
        if (started == null || started == false) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
        }
    }

    override fun cancelTask() {
        Log.d(LOG_TAG, "Before 'cancel' of job | thread: ${Thread.currentThread().name}")

        if (newJob == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
        } else {
            newJob?.cancel()
        }

        Log.d(LOG_TAG, "Before 'cancel' of job | thread: ${Thread.currentThread().name}")
    }

    private fun onProgressUpdate(progress: Int) {
        coroutineFragment?.updateFragmentText(progress.toString())
    }

    private fun onPreExecute() {
        coroutineFragment?.updateFragmentText("Job created")
    }

    private fun onPostExecute() {
        coroutineFragment?.updateFragmentText(getString(R.string.done))
        newJob = null
    }
}