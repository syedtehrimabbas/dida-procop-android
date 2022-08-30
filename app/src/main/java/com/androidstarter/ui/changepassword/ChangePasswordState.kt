package com.androidstarter.ui.changepassword

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class ChangePasswordState @Inject constructor() : BaseState(), IChangePassword.State {
    override var newPassword: MutableLiveData<String> = MutableLiveData()
    override var confirmPassword: MutableLiveData<String> = MutableLiveData()

}