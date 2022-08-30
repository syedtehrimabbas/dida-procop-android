package com.androidstarter.data.source.remote

import com.androidstarter.data.model.LoginRequest
import com.androidstarter.data.model.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RepositoryService {
    @POST("/wp-json/custom-plugin/login")
    suspend fun login(@Body loginRequest: LoginRequest?): Response<UserLoginResponse>
}