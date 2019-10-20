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

private const val FRAGMENT_TYPE = "fragment_type"

class CounterFragment : Fragment() {

    companion object {
        fun newInstance(fragmentTitle: String): CounterFragment {
            val fragment = CounterFragment()

            val bundle = Bundle(1).apply {
                putString(FRAGMENT_TYPE, fragmentTitle)
            }
            fragment.arguments = bundle

            return fragment
        }
    }

    private var taskEventsListener: TaskEventsListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_threads, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity != null && activity is TaskEventsListener) {
            taskEventsListener = activity as TaskEventsListener
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_create.setOnClickListener { taskEventsListener?.createTask() }
        button_start.setOnClickListener { taskEventsListener?.startTask() }
        button_cancel.setOnClickListener { taskEventsListener?.cancelTask() }

        //UNPACK OUR DATA FROM OUR BUNDLE
        val fragmentText = this.arguments?.getString(FRAGMENT_TYPE)
        text_value.text = fragmentText
    }

    override fun onDetach() {
        super.onDetach()

        taskEventsListener = null
    }

    fun updateFragmentText(text: String) {
        text_value.text = text
    }
}
