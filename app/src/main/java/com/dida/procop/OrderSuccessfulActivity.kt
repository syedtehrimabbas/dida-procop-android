package com.dida.procop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class OrderSuccessfulActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_successful)

        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            onBackPressed()
        }

    }
}