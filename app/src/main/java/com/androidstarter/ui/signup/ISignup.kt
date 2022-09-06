package com.androidstarter.ui.signup

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase

interface ISignup {
    interface State : IBase.State {
        var userName: MutableLiveData<String>
        var email: MutableLiveData<String>
        var password: MutableLiveData<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun onSignup()
        fun doSignup(userName:String,email:String,password:String)
    }
}