package com.androidstarter.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignupRequest(
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("password")
    var password: String? = "",
    @SerializedName("is_customer")
    var is_customer: Int? = 1,
) : Parcelable