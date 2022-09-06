package com.androidstarter.data.model


import com.androidstarter.data.BaseResponse
import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
) : BaseResponse()