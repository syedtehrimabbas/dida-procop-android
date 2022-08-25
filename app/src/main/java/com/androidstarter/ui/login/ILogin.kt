package com.androidstarter.ui.login

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase

interface ILogin {
    interface State : IBase.State {
        var email: MutableLiveData<String>
        var password: MutableLiveData<String>
        var isRemembered: MutableLiveData<Boolean>
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun onLogin()
    }
}