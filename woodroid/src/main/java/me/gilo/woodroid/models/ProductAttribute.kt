package me.gilo.woodroid.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductAttribute : Serializable {
    var id: Int = 0
    var name: String? = null
    var slug: String? = null
    var type: String? = null
    var position: Int = 0
    @SerializedName("visible")
    var isVisible: Boolean = false
    @SerializedName("variation")
    var isVariation: Boolean = false
    var options: ArrayList<String>? = arrayListOf()
    var selectedAttribute: String = ""
}
