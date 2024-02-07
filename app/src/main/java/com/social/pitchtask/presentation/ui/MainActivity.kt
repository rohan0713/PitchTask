package com.social.pitchtask.presentation.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.social.pitchtask.R
import com.social.pitchtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)
        window.statusBarColor = Color.WHITE

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            Intent(this@MainActivity, TasksActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }, 4000)
    }
}