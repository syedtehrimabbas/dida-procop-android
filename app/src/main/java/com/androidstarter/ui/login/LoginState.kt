package com.androidstarter.ui.login

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class LoginState @Inject constructor() : BaseState(), ILogin.State {
    override var email: MutableLiveData<String> = MutableLiveData("zaryab")
    override var password: MutableLiveData<String> = MutableLiveData("Zaryab@310A")
    override var isRemembered: MutableLiveData<Boolean> = MutableLiveData(false)
}