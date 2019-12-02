package by.androidacademy.firstapplication.androidservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

        viewModel?.run {
            downloadProgress().observe(this@ServiceFragment, Observer {
                setProgress(it.toString())
            })
            isButtonsEnable().observe(this@ServiceFragment, Observer {
                setSateForBtn(it)
            })
            isEnableDownloadService().observe(this@ServiceFragment, Observer { isEnable ->
                if (!isEnable) {
                    stopDownloadService()
                }
            })
            isEnableDownloadIntentService().observe(this@ServiceFragment, Observer { isEnable ->
                if (!isEnable) {
                    stopDownloadIntentService()
                }
            })
            isEnableDownloadJobIntentService().observe(this@ServiceFragment, Observer { isEnable ->
                if (!isEnable) {
                    stopDownloadJobIntentService()
                }
            })
        }

        btn_intent_service.setOnClickListener {
            setSateForBtn(false)
            activity?.run { serviceDelegate.startDownloadIntentService(this, true) }
        }

        bnt_service.setOnClickListener {
            setSateForBtn(false)
            activity?.run { serviceDelegate.startDownloadService(this, true) }
        }

        bnt_worker.setOnClickListener {
            setSateForBtn(false)
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
        activity?.run { serviceDelegate.stopAllService(this) }
    }

    private fun stopDownloadService() {
        activity?.run { serviceDelegate.stopDownloadService(this) }
    }

    private fun stopDownloadIntentService() {
        activity?.run { serviceDelegate.stopDownloadIntentService(this) }
    }

    private fun stopDownloadJobIntentService() {
        activity?.run { serviceDelegate.stopDownloadJobIntentService(this) }
    }

    private fun subscribeToUpdateWorker(params: WorkerParamsRequest) {
        activity?.run {
            params.workManagerInfo()
                .observe(this@ServiceFragment, Observer { workInfo ->
                    Toast.makeText(this, workInfo.state.name, Toast.LENGTH_SHORT).show()
                })
        }
    }

    private fun setProgress(progress: String) {
        activity?.runOnUiThread {
            tv_result_progress_intent.text = progress
        }
    }

    private fun setSateForBtn(isEnable: Boolean) {
        btn_intent_service.isEnabled = isEnable
        bnt_service.isEnabled = isEnable
        bnt_worker.isEnabled = isEnable

    }
}