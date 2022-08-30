package com.androidstarter.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginRequest(
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("password")
    var password: String? = "null",
) : Parcelable