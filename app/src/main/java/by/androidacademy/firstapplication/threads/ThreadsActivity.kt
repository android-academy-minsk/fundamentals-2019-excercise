package by.androidacademy.firstapplication.threads

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.androidacademy.firstapplication.R

class ThreadsActivity : AppCompatActivity(), TaskEventContract.Lifecycle,
    TaskEventContract.Operationable {

    private var threadsFragment: CounterFragment? = null
    private var task: SimpleAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val fragment = CounterFragment.newInstance(
                getString(R.string.fragment_handler_exe_title)
            ).also { threadsFragment = it }

            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit()
        }
    }

    override fun createTask() {
        Toast.makeText(this, getString(R.string.msg_thread_oncreate), Toast.LENGTH_SHORT).show()
        task = SimpleAsyncTask(this)
    }

    override fun startTask() {
        val taskCopy = task

        if (taskCopy == null || taskCopy.isCancelled) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.msg_thread_onstart), Toast.LENGTH_SHORT).show()
            task?.execute()
        }
    }

    override fun cancelTask() {
        if (task == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
        } else {
            task?.cancel()
        }
    }

    override fun onPreExecute() {
        Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show()

        threadsFragment?.updateFragmentText(getString(R.string.task_created))
    }

    override fun onPostExecute() {
        Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show()

        threadsFragment?.updateFragmentText(getString(R.string.done))
        task = null
    }

    override fun onProgressUpdate(progress: Int) {
        threadsFragment?.updateFragmentText(progress.toString())
    }

    override fun onCancel() {
        Toast.makeText(this, getString(R.string.msg_thread_oncancel), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        if (task != null) {
            task!!.cancel()
            task = null
        }
        super.onDestroy()
    }
}
