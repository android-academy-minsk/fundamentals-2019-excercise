package by.androidacademy.firstapplication.threads

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.androidacademy.firstapplication.R

class CoroutineActivity : AppCompatActivity(), TaskEventContract.Lifecycle, TaskEventContract.Operationable {

    private var coroutineFragment: CounterFragment? = null
    private var coroutineTask: CoroutineTask? = null

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

        coroutineTask?.cancel()
    }

    override fun createTask() {
        Toast.makeText(this, getString(R.string.msg_oncreate), Toast.LENGTH_SHORT).show()

        coroutineTask = CoroutineTask(this)
            .apply { createTask() }
    }

    override fun startTask() {
        val started = coroutineTask?.start()

        if (started == null || started == false) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
        }
    }

    override fun cancelTask() {
        val canceled = coroutineTask?.cancel()

        if (canceled == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onProgressUpdate(progress: Int) {
        coroutineFragment?.updateFragmentText(progress.toString())
    }

    override fun onPreExecute() {
        Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show()

        coroutineFragment?.updateFragmentText(getString(R.string.task_created))
    }

    override fun onPostExecute() {
        Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show()

        coroutineFragment?.updateFragmentText(getString(R.string.done))
    }

    override fun onCancel() {
        Toast.makeText(this, getString(R.string.msg_oncancel), Toast.LENGTH_SHORT).show()
    }
}