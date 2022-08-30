package com.androidstarter.ui.changepassword

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordVM @Inject constructor(
    override val viewState: ChangePasswordState,
    override var validator: Validator?
) : HiltBaseViewModel<IChangePassword.State>(), IChangePassword.ViewModel, IValidator {

    override fun onChangePasswordAPI() {
    }
}