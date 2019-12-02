package by.androidacademy.firstapplication.magicadditions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import by.androidacademy.firstapplication.R
import coil.api.load
import kotlinx.android.synthetic.main.activity_poster.*
import java.io.File

class PosterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val posterPath = intent.getStringExtra(POSTER_PATH)
        if (posterPath == null) {
            AlertDialog.Builder(this)
                .setMessage(R.string.generic_error)
                .setPositiveButton(R.string.dialog_ok) { _, _ ->
                    finishAfterTransition()
                }
                .create()
                .show()
            return
        }

        poster.load(File(posterPath))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {

        private const val POSTER_PATH = "POSTER_PATH"

        fun newIntent(context: Context, posterPath: String): Intent {
            return Intent(context, PosterActivity::class.java)
                .putExtra(POSTER_PATH, posterPath)
        }
    }
}