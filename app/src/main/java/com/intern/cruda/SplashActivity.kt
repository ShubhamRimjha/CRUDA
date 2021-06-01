package com.intern.cruda

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timer().schedule(2000) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

    }
}