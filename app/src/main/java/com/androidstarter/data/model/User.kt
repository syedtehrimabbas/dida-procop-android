package com.androidstarter.data.model


import com.androidstarter.data.BaseResponse
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("meta")
    val meta: List<Any> = listOf(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("slug")
    val slug: String = "",
    @SerializedName("url")
    val url: String = ""
) : BaseResponse()