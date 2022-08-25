package com.androidstarter.ui.signup

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupVM @Inject constructor(
    override val viewState: SignupState,
    override var validator: Validator?
) : HiltBaseViewModel<ISignup.State>(), ISignup.ViewModel, IValidator {
    override fun onSignup() {
    }
}