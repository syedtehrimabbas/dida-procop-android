package com.androidstarter.ui.login

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    override val viewState: LoginState,
    override var validator: Validator?
) : HiltBaseViewModel<ILogin.State>(), ILogin.ViewModel, IValidator {
    override fun onLogin() {

    }
}