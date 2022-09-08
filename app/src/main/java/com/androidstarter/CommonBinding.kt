package com.androidstarter

import android.widget.FrameLayout
import androidx.databinding.BindingAdapter

object CommonBinding {

    @JvmStatic
    @BindingAdapter(
        value = ["isSelected"],
        requireAll = false
    )
    fun setBackgroundSelected(
        frameLayout: FrameLayout,
        isSelected: Boolean
    ) {
        if (isSelected)
            frameLayout.setBackgroundResource(R.drawable.white_rounded_selected)
        else
            frameLayout.setBackgroundResource(R.drawable.white_rounded)
    }
}
