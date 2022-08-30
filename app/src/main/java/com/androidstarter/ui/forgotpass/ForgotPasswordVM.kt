package com.androidstarter.ui.forgotpass

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordVM @Inject constructor(
    override val viewState: ForgotPasswordState,
    override var validator: Validator?
) : HiltBaseViewModel<IForgotPassword.State>(), IForgotPassword.ViewModel, IValidator {
    override fun onForgot() {
    }
}