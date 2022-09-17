package com.androidstarter

import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.androidstarter.utils.DateUtils
import me.gilo.woodroid.models.ProductAttribute

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

    @JvmStatic
    @BindingAdapter("attributeValue")
    fun setAttributeValue(view: AppCompatTextView, productAttribute: ProductAttribute) {


        if (productAttribute.selectedAttribute.isNotEmpty()) {
            view.text = productAttribute.selectedAttribute
        } else {
            productAttribute.options?.let {
                if (it.size > 0)
                    view.text = it[0]
                else view.text = ""
            }
        }
    }

    @JvmStatic
    @BindingAdapter("orderDate")
    fun orderDate(view: AppCompatTextView, dateCreated: String) {
        val date = DateUtils.reformatStringDate(
            dateCreated,
            DateUtils.SERVER_DATE_FORMAT,
            DateUtils.FORMATE_DATE_MONTH_YEAR
        )
        view.text = "Ordered on $date"
    }
}
