package by.androidacademy.firstapplication.services

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.work.*
import by.androidacademy.firstapplication.dependency.AndroidServiceDelegate
import kotlinx.android.synthetic.main.fragment_bg_service.*
import java.util.concurrent.TimeUnit


class BGServiceFragment : Fragment() {

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
        return inflater.inflate(
            by.androidacademy.firstapplication.R.layout.fragment_bg_service,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_intent_service.setOnClickListener {
            btn_intent_service.isEnabled = false
            activity?.run {
                val intent = Intent(activity, IntentServiceProgress::class.java)
                startService(intent)
            }
        }
        bnt_service.setOnClickListener {
            bnt_service.isEnabled = false
            activity?.run {
                val intent = Intent(this, ServiceProgress::class.java)
                startService(intent)
            }
        }

        bnt_worker.setOnClickListener {
            bnt_worker.isEnabled = false
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

// Define the input
            val imageData = workDataOf("URI" to "")

// Bring it all together by creating the WorkRequest; this also sets the back off criteria
            val uploadWorkRequest = OneTimeWorkRequestBuilder<WorkerManager>()
                    .setInputData(imageData)
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
            activity?.run {
                WorkManager.getInstance(this).enqueue(uploadWorkRequest)
                WorkManager.getInstance(this).getWorkInfoByIdLiveData(uploadWorkRequest.id).observe(this, Observer {
                    it?.let {
                        if (it.state == WorkInfo.State.SUCCEEDED) {
                            AndroidServiceDelegate.setProgressToWorkerManager(100)
                        }else {
                            AndroidServiceDelegate.setProgressToWorkerManager(-1)
                        }
                    }
                })
            }
        }

        AndroidServiceDelegate.subscribeToUpdaterWorkerManagerProgress().observe(this,
            Observer { progress ->
                bnt_worker.isEnabled = true
                setProgress(progress.toString())
            })

        AndroidServiceDelegate.subscribeToServiceProgress().observe(this, Observer { progress ->
            setProgress(progress.toString())
            if (progress == 100) {
                bnt_service.isEnabled = true
                setProgress("")
                activity?.run {
                    val intent = Intent(this, ServiceProgress::class.java)
                    stopService(intent)
                }
            }
        })
        AndroidServiceDelegate.subscribeToIntentServiceProgress()
            .observe(this, Observer { progress ->
                setProgressIntent(progress.toString())
                if (progress == 100) {
                    btn_intent_service.isEnabled = true
                    setProgressIntent("")
                    activity?.run {
                        val intent = Intent(this, IntentServiceProgress::class.java)
                        stopService(intent)
                    }
                }
            })
    }

    private fun setProgress(progress: String) {
        tv_progress.text = progress
    }

    private fun setProgressIntent(progress: String) {
        tv_result_progress_intent.text = progress
    }
}