package com.dida.procop

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            onBackPressed()
        }
    }
}