package com.androidstarter.data.model


import com.androidstarter.data.BaseResponse
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("iat")
    val iat: Int,
    @SerializedName("jwt_token")
    val jwtToken: String,
    @SerializedName("token_type")
    val tokenType: String
):BaseResponse()