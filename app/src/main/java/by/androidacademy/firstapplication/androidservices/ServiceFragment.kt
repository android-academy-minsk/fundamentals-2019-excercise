package by.androidacademy.firstapplication.androidservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.WorkInfo
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.androidservices.viewmodel.ServiceViewModel
import by.androidacademy.firstapplication.androidservices.viewmodel.ServiceViewModelFactory
import by.androidacademy.firstapplication.dependency.Dependencies
import kotlinx.android.synthetic.main.fragment_bg_service.*


class ServiceFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "fragment_counterFragment"
        fun newInstance(): ServiceFragment {
            return ServiceFragment()
        }
    }

    private val serviceDelegate: ServiceDelegate = Dependencies.serviceDelegate
    private var viewModel: ServiceViewModel? = null

    private val progressDownloadWorker: Observer<WorkInfo> = Observer {
        it?.let {
            if (it.state == WorkInfo.State.SUCCEEDED) {
                setProgress("100")
            } else {
                setProgress("0")
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ServiceViewModelFactory(
                Dependencies.heavyWorkManager,
                Dependencies.workerParamsRequest
            )
        ).get(ServiceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_bg_service,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        viewModel?.getProgress()?.observe(this, Observer {
            setProgress(it.toString())
        })

        btn_intent_service.setOnClickListener {
            activity?.run { serviceDelegate.startDownloadIntentService(this, true) }
        }

        bnt_service.setOnClickListener {
            activity?.run { serviceDelegate.startDownloadService(this) }
        }

        bnt_worker.setOnClickListener {
            viewModel?.run {
                getWorker().run {
                    enqueue()
                    subscribeToUpdateWorker(this)
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.cancelWork()
        activity?.run { serviceDelegate.stopAllService(this) }
    }

    private fun subscribeToUpdateWorker(params: WorkerParamsRequest) {
        activity?.run {
            params.workManagerInfo()
                .observe(this@ServiceFragment, progressDownloadWorker)
        }
    }

    private fun setProgress(progress: String) {
        activity?.runOnUiThread {
            tv_result_progress_intent.text = progress
        }
    }
}