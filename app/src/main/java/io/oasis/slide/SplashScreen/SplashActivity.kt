package io.oasis.slide.SplashScreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import io.oasis.slide.MainActivity
import io.oasis.slide.R
import org.jetbrains.anko.intentFor

class SplashActivity : AppCompatActivity() {

    val SPLASH_TIME : Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            startActivity(intentFor<MainActivity>())
            finish()
        },SPLASH_TIME)
    }
}
