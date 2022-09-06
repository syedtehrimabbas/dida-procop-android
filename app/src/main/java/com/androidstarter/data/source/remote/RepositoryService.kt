package com.androidstarter.data.source.remote

import com.androidstarter.data.model.LoginRequest
import com.androidstarter.data.model.SignupRequest
import com.androidstarter.data.model.SignupResponse
import com.androidstarter.data.model.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RepositoryService {
    @POST("wp-json/custom-plugin/login")
    suspend fun login(@Body loginRequest: LoginRequest?): Response<UserLoginResponse>

    @POST("wp-json/wp/v2/users/register")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<SignupResponse>
}