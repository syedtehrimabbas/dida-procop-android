package com.androidstarter.data.model


import com.androidstarter.data.ApiResponse
import com.androidstarter.data.BaseResponse
import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("data")
    val user: User
): BaseResponse()

data class User(
    @SerializedName("display_name")
    val displayName: String="",
    @SerializedName("ID")
    val iD: String="",
    @SerializedName("user_activation_key")
    val userActivationKey: String="",
    @SerializedName("user_email")
    val userEmail: String="",
    @SerializedName("user_login")
    val userLogin: String="",
    @SerializedName("user_nicename")
    val userNicename: String="",
    @SerializedName("user_registered")
    val userRegistered: String="",
    @SerializedName("user_status")
    val userStatus: String="",
    @SerializedName("user_url")
    val userUrl: String=""
) : BaseResponse()

