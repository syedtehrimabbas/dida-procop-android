package com.androidstarter.ui.about

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutVM @Inject constructor(
    override val viewState: AboutState,
    override var validator: Validator?,
    val databaseHelper: DatabaseHelper
) : HiltBaseViewModel<IAbout.State>(), IAbout.ViewModel, IValidator {

}