package com.androidstarter.data.source.remote

import com.androidstarter.data.BaseRepository
import com.androidstarter.data.model.LoginRequest
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
}