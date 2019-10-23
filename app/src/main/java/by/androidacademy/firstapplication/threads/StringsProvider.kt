package by.androidacademy.firstapplication.threads

import android.content.Context
import androidx.annotation.StringRes

class StringsProvider(val context: Context) {

    fun getString(@StringRes stringResId: Int) = context.getString(stringResId)
}