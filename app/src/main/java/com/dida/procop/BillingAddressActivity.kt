package com.dida.procop

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class BillingAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billing_address)

        findViewById<AppCompatButton>(R.id.checkoutBT).setOnClickListener {
            val intent = Intent(
                this@BillingAddressActivity,
                CardActivity::class.java
            )
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            onBackPressed()
        }
    }
}