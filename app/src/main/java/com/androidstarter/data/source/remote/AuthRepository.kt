package com.androidstarter.data.source.remote

import com.androidstarter.data.ApiResponse
import com.androidstarter.data.BaseRepository
import com.androidstarter.data.model.LoginRequest
import com.androidstarter.data.model.OrderRequest
import com.androidstarter.data.model.SignupRequest
import com.androidstarter.data.model.SignupResponse
import javax.inject.Inject

class AuthRepository @Inject
constructor(
    private val service: RepositoryService
) : BaseRepository(), IAuthRepository {
    override suspend fun login(loginRequest: LoginRequest?) = executeSafely(
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
        executeSafely(
            call =
            {
                service.createOrder(orderRequest)
            }
        )
}