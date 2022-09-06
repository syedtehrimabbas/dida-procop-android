package com.androidstarter.ui.login

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.ApiResponse
import com.androidstarter.data.model.LoginRequest
import com.androidstarter.data.source.remote.IAuthRepository
import com.androidstarter.data.sessions.SessionManager
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
            viewState.email.value.toString(),
            viewState.password.value.toString()
        )
    }

    override fun doLogin(userName: String, password: String) {
        val request = LoginRequest(username = userName, password = password)
        launch {
            loading(true)
            when (val response = repository.login(request)) {

                is ApiResponse.Success -> {
                    val isRemember = viewState.isRemembered.value ?: false
                    sessionManager.startUserSession(response.data.user, isRemember)
                    loading(false,"Login successful")
                    clickEvent?.postValue(200)
                }

                is ApiResponse.Error -> {
                    loading(false, response.error.message)
                }
            }
        }
    }
}