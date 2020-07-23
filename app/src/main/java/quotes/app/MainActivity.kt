package quotes.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.*
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView.setupWithNavController(findNavController(R.id.fragment))

    }
}

/**
 *
This project has been created by Abdallah El-Sakka in 13/7/20

Contact me on :

Whatsapp : +201010406009
Facebook : https://www.facebook.com/abdallassam
Github : https://github.com/AbdallahEssam501

All Rights Reserved.

 **/
