package by.androidacademy.firstapplication.threads

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class CoroutineActivity : AppCompatActivity(),
    TaskEventContract.Operationable {

    private var coroutineFragment: CounterFragment? = null
    private lateinit var viewModel: CoroutinesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val fragment = CounterFragment.newInstance().also { coroutineFragment = it }

            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment, FRAGMENT_TAG)
                .commit()
        } else {
            coroutineFragment =
                supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as CounterFragment
        }

        viewModel = ViewModelProviders
            .of(this, CoroutinesViewModelFactory(this))
            .get(CoroutinesViewModel::class.java)
        viewModel.text.observe(
            this,
            Observer { text -> coroutineFragment?.updateFragmentText(text) }
        )
    }

    override fun createTask() {
        viewModel.onCreateTask()
    }

    override fun startTask() {
        viewModel.onStartTask()
    }

    override fun cancelTask() {
        viewModel.onCancelTask()
    }

    companion object {
        private const val FRAGMENT_TAG = "fragment_tag"
    }
}
