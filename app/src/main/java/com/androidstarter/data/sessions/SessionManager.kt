package com.androidstarter.data.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.*
import com.androidstarter.data.model.User

class SessionManager constructor(
    private val sharedPreferenceManager: SharedPreferenceManager
) {
    fun startUserSession(user: User, isRemember: Boolean) {
        sharedPreferenceManager.save(KEY_IS_REMEMBER, isRemember)
        sharedPreferenceManager.save(
            KEY_IS_USER_LOGGED_IN,
            true
        )
        sharedPreferenceManager.save(KEY_USER, user.toString())
        sharedPreferenceManager.save(KEY_USER_NAME, user.displayName)
        sharedPreferenceManager.save(KEY_USER_EMAIL, user.userEmail)
        _user.postValue(user)
    }

    fun endUserSession() {
        sharedPreferenceManager.removeValue(KEY_IS_REMEMBER)
        sharedPreferenceManager.removeValue(KEY_IS_USER_LOGGED_IN)
        sharedPreferenceManager.removeValue(KEY_USER)
        _user = MutableLiveData()
    }

    companion object {
        private var _user: MutableLiveData<User> = MutableLiveData()
        var user: LiveData<User> = _user
    }

    fun setUser(){
        val name = sharedPreferenceManager.getValueString(KEY_USER_NAME) ?: ""
        val email = sharedPreferenceManager.getValueString(KEY_USER_EMAIL) ?: ""
        _user.value = User(displayName = name, userEmail = email)
    }
}