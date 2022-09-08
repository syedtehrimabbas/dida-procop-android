package com.androidstarter.data.source.remote

import com.androidstarter.data.ApiResponse
import com.androidstarter.data.model.*

interface IAuthRepository {
    suspend fun login(loginRequest: LoginRequest?): ApiResponse<UserLoginResponse>
    suspend fun signup(signupRequest: SignupRequest): ApiResponse<SignupResponse>
    suspend fun createOrder(orderRequest: OrderRequest): ApiResponse<OrderResponse>
}