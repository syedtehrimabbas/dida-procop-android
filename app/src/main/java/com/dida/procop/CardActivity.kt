package com.dida.procop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            onBackPressed()
        }
    }
}