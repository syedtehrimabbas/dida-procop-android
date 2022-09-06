package com.androidstarter.data.source.remote

import com.androidstarter.data.ApiResponse
import com.androidstarter.data.model.LoginRequest
import com.androidstarter.data.model.SignupRequest
import com.androidstarter.data.model.SignupResponse
import com.androidstarter.data.model.UserLoginResponse

interface IAuthRepository {
    suspend fun login(loginRequest: LoginRequest?): ApiResponse<UserLoginResponse>
    suspend fun signup(signupRequest: SignupRequest): ApiResponse<SignupResponse>
}