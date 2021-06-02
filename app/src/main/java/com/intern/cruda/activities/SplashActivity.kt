package com.intern.cruda.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.intern.cruda.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_ativity)

/* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        Handler().postDelayed(
            {
                overridePendingTransition(R.anim.anim_in, R.anim.screen_out)
                startActivity(
                    Intent(
                        this@SplashActivity, MainActivity::class.java
                    ),
                )
                finish()
            }, 2100
        )
    }
}

