package com.androidstarter.data.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.KEY_ID
import com.androidstarter.base.KEY_IS_REMEMBER
import com.androidstarter.base.KEY_IS_USER_LOGGED_IN
import com.androidstarter.base.KEY_USER
import com.androidstarter.base.KEY_USER_EMAIL
import com.androidstarter.base.KEY_USER_NAME
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
        sharedPreferenceManager.save(KEY_ID, user.id)
        sharedPreferenceManager.save(KEY_USER, user.toString())
        sharedPreferenceManager.save(KEY_USER_NAME, user.name.substringBefore("@"))
        sharedPreferenceManager.save(KEY_USER_EMAIL, user.email)
        _user.postValue(user)
    }

    fun endUserSession() {
        sharedPreferenceManager.removeValue(KEY_IS_REMEMBER)
        sharedPreferenceManager.removeValue(KEY_IS_USER_LOGGED_IN)
        sharedPreferenceManager.removeValue(KEY_USER)
        _user = MutableLiveData()
    }
    fun getUserId() =  sharedPreferenceManager.getValueInt(KEY_ID)

    companion object {
        private var _user: MutableLiveData<User> = MutableLiveData()
        var user: LiveData<User> = _user
        var IS_USER_LOGIN = false
    }

    fun setUser() {
        val name = sharedPreferenceManager.getValueString(KEY_USER_NAME) ?: ""
        val email = sharedPreferenceManager.getValueString(KEY_USER_EMAIL) ?: ""
        val id = sharedPreferenceManager.getValueInt(KEY_ID)
        _user.value = User(name = name.substringBefore("@"), email = email, id = id)
    }
}