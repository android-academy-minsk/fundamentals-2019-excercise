package by.androidacademy.firstapplication.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BGServiceActivity : AppCompatActivity(){

    private var bgServiceFragment: BGServiceFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val fragment = BGServiceFragment.newInstance().also { bgServiceFragment = it }

            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment, BGServiceFragment.FRAGMENT_TAG)
                .commit()
        } else {
            bgServiceFragment =
                supportFragmentManager.findFragmentByTag(BGServiceFragment.FRAGMENT_TAG) as? BGServiceFragment
        }

    }

}
