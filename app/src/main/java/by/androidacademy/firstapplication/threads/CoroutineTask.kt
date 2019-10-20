package by.androidacademy.firstapplication.threads

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class CoroutineTask(private val taskEventsListener: TaskEventsListener) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob()

    private var newJob: Job? = null
}