package com.dida.procop

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class OrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            onBackPressed()
        }
    }
}