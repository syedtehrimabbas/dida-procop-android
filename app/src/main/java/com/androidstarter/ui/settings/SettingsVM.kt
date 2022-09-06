package com.androidstarter.ui.settings

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    override val viewState: SettingsState,
    override var validator: Validator?,
) : HiltBaseViewModel<ISettings.State>(), ISettings.ViewModel, IValidator {

}