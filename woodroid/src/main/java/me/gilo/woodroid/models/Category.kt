package me.gilo.woodroid.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var id: Int = 0,
    var name: String? = "",
    var slug: String? = "",
    var parent: Int = 0,
    var description: String = "",
    var display: String = "",
    var image: Image? = null,
    var menu_order: Int = 0,
    var count: Int = 0
) : Parcelable
