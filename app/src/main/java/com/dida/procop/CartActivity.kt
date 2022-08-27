package com.dida.procop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        findViewById<Button>(R.id.checkoutButton).setOnClickListener { view: View? ->
            val intent = Intent(
                this@CartActivity,
                BillingAddressActivity::class.java
            )
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.closeBtn).setOnClickListener(View.OnClickListener { finish() })
    }
}