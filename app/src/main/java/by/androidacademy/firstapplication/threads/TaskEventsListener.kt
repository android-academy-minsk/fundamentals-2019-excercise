package by.androidacademy.firstapplication.threads

interface TaskEventsListener {

    fun createAsyncTask()
    fun startAsyncTask()
    fun cancelAsyncTask()

    fun onPreExecute()
    fun onPostExecute()
    fun onProgressUpdate(integer: Int)
    fun onCancel()
}