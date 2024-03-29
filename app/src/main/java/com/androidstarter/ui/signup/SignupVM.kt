package com.androidstarter.ui.signup

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.ApiResponse
import com.androidstarter.data.model.SignupRequest
import com.androidstarter.data.sessions.SessionManager
import com.androidstarter.data.source.remote.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupVM @Inject constructor(
    override val viewState: SignupState,
    override var validator: Validator?,
    private val repository: IAuthRepository,
    private val sessionManager: SessionManager
) : HiltBaseViewModel<ISignup.State>(), ISignup.ViewModel, IValidator {
    override fun onSignup() {
        with(viewState) {
            doSignup(
                email.value.toString(),
                password.value.toString(),
            )
        }
    }

    override fun doSignup(email: String, password: String) {
        val request = SignupRequest(username = email, password = password, email = email)
        launch {
            loading(true)
            when (val response = repository.signup(request)) {
                is ApiResponse.Success -> {
                    if (response.data.code == 200) {
                        loading(false, "Signup successful")
                        clickEvent?.postValue(200)
                    } else {
                        loading(false, "Signup failed")
                    }
                }

                is ApiResponse.Error -> {
                    loading(false, "Signup failed")
                }
            }
        }
    }
}