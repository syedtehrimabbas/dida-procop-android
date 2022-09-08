package com.androidstarter.data.source.remote

import com.androidstarter.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RepositoryService {
    @POST("wp-json/custom-plugin/login")
    suspend fun login(@Body loginRequest: LoginRequest?): Response<UserLoginResponse>

    @POST("wp-json/wp/v2/users/register")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<SignupResponse>

    @Headers("Content-Type: application/json")
    @POST("wp-json/wc/v3/orders?consumer_key=ck_c70e62bd7b8f6e2dce406a127f9ab6dd11d98d45&consumer_secret=cs_6bb3e7ca80db9d9a87514dd37fdee88dbbd22034")
    suspend fun createOrder(@Body body: OrderRequest): Response<OrderResponse>
}