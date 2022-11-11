package com.androidstarter.ui.settings

import android.content.SharedPreferences
import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.sessions.SharedPreferenceManager
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    override val viewState: SettingsState,
    override var validator: Validator?,
    val databaseHelper: DatabaseHelper,
    val sharedPreferenceManager: SharedPreferenceManager
) : HiltBaseViewModel<ISettings.State>(), ISettings.ViewModel, IValidator {

}