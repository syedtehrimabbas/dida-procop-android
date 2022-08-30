package com.androidstarter.ui.changepassword

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase

interface IChangePassword {
    interface State : IBase.State {
        var newPassword: MutableLiveData<String>
        var confirmPassword: MutableLiveData<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun onChangePasswordAPI()
    }
}