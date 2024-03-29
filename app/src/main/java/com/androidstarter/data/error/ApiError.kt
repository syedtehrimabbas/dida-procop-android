package com.androidstarter.data.error

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiError(
    @SerializedName("code") var statusCode: Int,
    @SerializedName("message") var message: String = ""
)