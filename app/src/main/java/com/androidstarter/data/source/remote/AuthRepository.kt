package com.androidstarter.data.source.remote

import com.androidstarter.data.ApiResponse
import com.androidstarter.data.BaseRepository
import com.androidstarter.data.model.*
import javax.inject.Inject

class AuthRepository @Inject
constructor(
    private val service: RepositoryService
) : BaseRepository(), IAuthRepository {
    override suspend fun login(loginRequest: LoginRequest?): ApiResponse<LoginResponse> =
        executeSafely(
            call =
            {
                service.login(loginRequest)
            }
        )

    override suspend fun signup(signupRequest: SignupRequest): ApiResponse<SignupResponse> =
        executeSafely(
            call =
            {
                service.signup(signupRequest)
            }
        )

    override suspend fun createOrder(orderRequest: OrderRequest): ApiResponse<OrderResponse> =
        executeSafely(call = { service.createOrder(orderRequest) })

    override suspend fun fetchUser(auth: String): ApiResponse<User> =
        executeSafely(call = { service.fetchUser(auth) })
}