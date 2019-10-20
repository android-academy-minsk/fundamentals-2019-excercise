package by.androidacademy.firstapplication.threads

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.androidacademy.firstapplication.R
import kotlinx.android.synthetic.main.activity_main.button_create
import kotlinx.android.synthetic.main.activity_main.button_start
import kotlinx.android.synthetic.main.activity_main.button_stop
import kotlinx.android.synthetic.main.activity_main.text_counter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CoroutineActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        private const val LOG_TAG = "CoroutineActivity"
        private const val MAX_COUNTER_VALUE = 10
        private const val DELAY_IN_MILLS = 500L
    }

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob()

    private var newJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_create.setOnClickListener {
            Toast.makeText(
                this@CoroutineActivity,
                getString(R.string.msg_oncreate),
                Toast.LENGTH_SHORT
            ).show()

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
                        text_counter.text = counter.toString()
                    }

                    delay(DELAY_IN_MILLS)
                }

                launch(Dispatchers.Main) {
                    Log.d(
                        LOG_TAG,
                        "Switch thread to main again | thread: ${Thread.currentThread().name}"
                    )
                    text_counter.text = "Job normally ends"
                }
            }

            text_counter.text = "Job created"
        }

        button_start.setOnClickListener {
            Log.d(LOG_TAG, "Before 'start' of job | thread: ${Thread.currentThread().name}")
            val started = newJob?.start()
            Log.d(
                LOG_TAG,
                "After 'start' of job (started: $started) | thread: ${Thread.currentThread().name}"
            )
            if (started == null) {
                Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
            }
        }

        button_stop.setOnClickListener {
            Log.d(LOG_TAG, "Before 'cancel' of job | thread: ${Thread.currentThread().name}")
            newJob?.cancel()
            Log.d(LOG_TAG, "Before 'cancel' of job | thread: ${Thread.currentThread().name}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        newJob?.cancel()
        coroutineContext.cancel()
    }
}