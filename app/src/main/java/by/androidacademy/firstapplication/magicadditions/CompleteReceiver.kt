package by.androidacademy.firstapplication.magicadditions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class CompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("CompleteReceiver", "#onReceive")
        Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show()
        val posterPath = intent.getStringExtra(DownloadService.POSTER_PATH) ?: return
        Log.d("CompleteReceiver", "#onReceive, posterPath: $posterPath")
        val trailerIntent = PosterActivity.newIntent(context, posterPath)
        context.startActivity(trailerIntent)
    }
}