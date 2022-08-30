package com.androidstarter.ui.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.KEY_IS_REMEMBER
import com.androidstarter.base.KEY_IS_USER_LOGGED_IN
import com.androidstarter.base.KEY_USER
import com.androidstarter.data.model.User

class SessionManager constructor(
    private val sharedPreferenceManager: SharedPreferenceManager
) {
    private var _user: MutableLiveData<User> = MutableLiveData()
    var user: LiveData<User> = _user

    fun startUserSession(user: User, isRemember: Boolean) {
        sharedPreferenceManager.save(KEY_IS_REMEMBER, isRemember)
        sharedPreferenceManager.save(
            KEY_IS_USER_LOGGED_IN,
            true
        )
        sharedPreferenceManager.save(KEY_USER, user.toString())
        _user.postValue(user)
    }

    fun endUserSession() {
        sharedPreferenceManager.removeValue(KEY_IS_REMEMBER)
        sharedPreferenceManager.removeValue(KEY_IS_USER_LOGGED_IN)
        sharedPreferenceManager.removeValue(KEY_USER)
        _user = MutableLiveData()
    }
}