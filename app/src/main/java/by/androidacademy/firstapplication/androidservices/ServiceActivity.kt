package by.androidacademy.firstapplication.androidservices

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    android.R.id.content,
                    ServiceFragment.newInstance(),
                    ServiceFragment.FRAGMENT_TAG
                )
                .commit()
        }
    }

}
