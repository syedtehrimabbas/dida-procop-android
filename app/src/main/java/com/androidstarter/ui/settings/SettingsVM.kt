package com.androidstarter.ui.settings

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    override val viewState: SettingsState,
    override var validator: Validator?,
    val databaseHelper: DatabaseHelper
) : HiltBaseViewModel<ISettings.State>(), ISettings.ViewModel, IValidator {

}