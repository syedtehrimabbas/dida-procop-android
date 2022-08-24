package com.dida.procop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        findViewById<AppCompatButton>(R.id.payBT).setOnClickListener {
            val intent = Intent(
                this@CardActivity,
                OrderSuccessfulActivity::class.java
            )
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            onBackPressed()
        }
    }
}