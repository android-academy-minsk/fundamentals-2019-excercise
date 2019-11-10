package by.androidacademy.firstapplication.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_bg_service.*



class BGServiceFragment: Fragment() {

    companion object {
        const val FRAGMENT_TAG = "fragment_counterFragment"
        fun newInstance(): BGServiceFragment {
            return BGServiceFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(by.androidacademy.firstapplication.R.layout.fragment_bg_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_intent_service.setOnClickListener {  }
        bnt_service.setOnClickListener {  }
    }
}