package com.intern.cruda.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Path
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.intern.cruda.R
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val car_anim = findViewById<ImageView>(R.id.car_anim)

        val path = Path().apply {
            arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
        }
        val anim = ObjectAnimator.ofFloat(car_anim, View.X, View.Y, path).apply {
            duration = 2000
            start()
        }


        startActivity(
            Intent(
                this@SplashActivity, MainActivity::class.java
            )
        )
        overridePendingTransition(R.anim.screen_in, R.anim.screen_out)
        finishAfterTransition()

    }
}