package com.androidstarter.ui.forgotpass

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase

interface IForgotPassword {
    interface State : IBase.State {
        var email: MutableLiveData<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun onForgot()
    }
}