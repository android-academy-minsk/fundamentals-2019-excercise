package by.androidacademy.firstapplication.threads

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.androidacademy.firstapplication.R
import kotlinx.android.synthetic.main.fragment_threads.button_cancel
import kotlinx.android.synthetic.main.fragment_threads.button_create
import kotlinx.android.synthetic.main.fragment_threads.button_start
import kotlinx.android.synthetic.main.fragment_threads.text_value

class CounterFragment : Fragment() {

    companion object {
        fun newInstance(): CounterFragment {
            return CounterFragment()
        }
    }

    private var listener: TaskEventContract.Operationable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_threads, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity != null && activity is TaskEventContract.Operationable) {
            listener = activity as TaskEventContract.Operationable
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_create.setOnClickListener { listener?.createTask() }
        button_start.setOnClickListener { listener?.startTask() }
        button_cancel.setOnClickListener { listener?.cancelTask() }
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    fun updateFragmentText(text: String) {
        text_value.text = text
    }
}
