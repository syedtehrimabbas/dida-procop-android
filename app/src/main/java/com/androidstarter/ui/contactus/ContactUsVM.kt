package com.androidstarter.ui.contactus

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactUsVM @Inject constructor(
    override val viewState: ContactUsState,
    override var validator: Validator?,
    val databaseHelper: DatabaseHelper
) : HiltBaseViewModel<IContactUs.State>(), IContactUs.ViewModel, IValidator {

}