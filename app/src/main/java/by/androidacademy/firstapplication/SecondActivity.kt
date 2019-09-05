package by.androidacademy.firstapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textView: TextView = findViewById(R.id.textView)
        textView.setOnClickListener {
            openGoogle()
        }
    }

    private fun openGoogle() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"))
        startActivity(intent)
    }
}
