package com.androidstarter.ui.login

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.ApiResponse
import com.androidstarter.data.model.LoginRequest
import com.androidstarter.data.source.remote.IAuthRepository
import com.androidstarter.data.sessions.SessionManager
import com.androidstarter.data.sessions.SessionManager.Companion.IS_USER_LOGIN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    override val viewState: LoginState,
    override var validator: Validator?,
    private val repository: IAuthRepository,
    private val sessionManager: SessionManager
) : HiltBaseViewModel<ILogin.State>(), ILogin.ViewModel, IValidator {

    override fun onLogin() {
        doLogin(
            viewState.email.value.toString(), viewState.password.value.toString()
        )
    }

    override fun doLogin(userName: String, password: String) {
        val request = LoginRequest(username = userName, password = password)
        launch {
            loading(true)
            when (val response = repository.login(request)) {

                is ApiResponse.Success -> {
                    IS_USER_LOGIN = true
                    fetchUser("Bearer ${response.data.jwtToken}", userName)
                }

                is ApiResponse.Error -> loading(false, response.error.message)
            }
        }
    }

    private fun fetchUser(auth: String, userName: String) {
        launch {
            when (val response = repository.fetchUser(auth)) {

                is ApiResponse.Success -> {
                    val isRemember = viewState.isRemembered.value ?: false
//                    response.data.email = userName
                    sessionManager.startUserSession(response.data, isRemember)
                    loading(false, "Login successful")
                    clickEvent?.postValue(200)
                }

                is ApiResponse.Error -> loading(false, response.error.message)
            }
        }
    }
}