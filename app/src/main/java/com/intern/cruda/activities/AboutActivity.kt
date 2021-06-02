package com.intern.cruda.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.imageview.ShapeableImageView
import com.intern.cruda.R

class AboutActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var img: ShapeableImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        toolbar = findViewById(R.id.toolbar)
        img = findViewById(R.id.me_img)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_wheel)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "About Me!"


    }

    override fun onBackPressed() {
        overridePendingTransition(R.anim.screen_in, R.anim.screen_out)
        super.onBackPressed()
    }
}