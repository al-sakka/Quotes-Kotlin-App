package quotes.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val background = object : Thread(){
            override fun run() {
                try {
                    Thread.sleep(3000)

                    val intent = Intent(baseContext , MainActivity::class.java)
                    startActivity(intent)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()

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