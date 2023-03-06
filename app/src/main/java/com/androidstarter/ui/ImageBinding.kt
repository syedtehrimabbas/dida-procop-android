package com.androidstarter.ui

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.dida.procop.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object ImageBinding {

    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl"],
        requireAll = false
    )
    fun loadProductImage(
        imageView: AppCompatImageView,
        imageUrl: String?,
    ) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.drawable.sample_image2)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter(
        value = ["quantity", "unitPrice"],
        requireAll = false
    )
    fun setCartItemPrice(
        textView: TextView,
        quantity: Int,
        unitPrice: Double
    ) {
        val totalPrice = String.format("%.2f", (quantity * unitPrice))
        textView.text = "€ $totalPrice"
    }
}
