package by.androidacademy.firstapplication.threads

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.androidacademy.firstapplication.utils.StringsProvider
import java.lang.IllegalArgumentException

class CoroutinesViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == CoroutinesViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            CoroutinesViewModel(StringsProvider(context)) as T
        } else {
            throw IllegalArgumentException("Unknown view model class $modelClass")
        }
    }
}