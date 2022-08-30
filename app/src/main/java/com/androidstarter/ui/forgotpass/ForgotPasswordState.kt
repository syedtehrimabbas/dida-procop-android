package com.androidstarter.ui.forgotpass

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class ForgotPasswordState @Inject constructor() : BaseState(), IForgotPassword.State {
    override var email: MutableLiveData<String> = MutableLiveData()
}