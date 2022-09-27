package com.androidstarter.ui.profile

import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.sessions.SessionManager
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    override val viewState: ProfileState,
    val databaseHelper: DatabaseHelper,
    val sessionManager: SessionManager,
    ) : HiltBaseViewModel<IProfile.State>(), IProfile.ViewModel {

}