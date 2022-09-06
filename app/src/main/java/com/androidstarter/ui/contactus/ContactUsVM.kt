package com.androidstarter.ui.contactus

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactUsVM @Inject constructor(
    override val viewState: ContactUsState,
    override var validator: Validator?,
) : HiltBaseViewModel<IContactUs.State>(), IContactUs.ViewModel, IValidator {

}