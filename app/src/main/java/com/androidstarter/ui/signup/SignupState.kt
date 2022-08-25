package com.androidstarter.ui.signup

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class SignupState @Inject constructor() : BaseState(), ISignup.State {
    override var email: MutableLiveData<String> = MutableLiveData()
    override var password: MutableLiveData<String> = MutableLiveData()
    override var userName: MutableLiveData<String> = MutableLiveData()
}