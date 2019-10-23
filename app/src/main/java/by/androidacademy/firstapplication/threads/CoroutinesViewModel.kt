package by.androidacademy.firstapplication.threads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.androidacademy.firstapplication.R

class CoroutinesViewModel(val stringsProvider: StringsProvider) : ViewModel() {

    private val textMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private var coroutineTask: CoroutineTask? = null
    private val listener: TaskEventContract.Lifecycle = object  : TaskEventContract.Lifecycle {
        override fun onProgressUpdate(progress: Int) {
            textMutableLiveData.value = progress.toString()
        }

        override fun onPreExecute() = Unit

        override fun onPostExecute() {
            textMutableLiveData.value = stringsProvider.getString(R.string.done)
        }

        override fun onCancel() = Unit
    }

    val text: LiveData<String> = textMutableLiveData

    fun onCreateTask() {
        textMutableLiveData.value = stringsProvider.getString(R.string.msg_oncreate)

        coroutineTask = CoroutineTask(listener)
            .apply { createTask() }
    }

    fun onStartTask() {
        val started = coroutineTask?.start()

        if (started == null || started == false) {
            textMutableLiveData.value = stringsProvider.getString(R.string.msg_should_create_task)
        }
    }

    fun onCancelTask() {
        val canceled = coroutineTask?.cancel()

        if (canceled == null) {
            textMutableLiveData.value = stringsProvider.getString(R.string.msg_should_create_task)
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineTask?.cancel()
    }
}