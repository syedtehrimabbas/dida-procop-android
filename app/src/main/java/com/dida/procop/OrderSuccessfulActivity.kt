package com.dida.procop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class OrderSuccessfulActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_successful)

        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            onBackPressed()
        }

        findViewById<Button>(R.id.myOrdersBt).setOnClickListener {
            val intent = Intent(this@OrderSuccessfulActivity, OrdersActivity::class.java)
            startActivity(intent)
        }

    }
}