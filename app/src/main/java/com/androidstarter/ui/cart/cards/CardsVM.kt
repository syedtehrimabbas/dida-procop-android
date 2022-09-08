package com.androidstarter.ui.cart.cards

import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsVM @Inject constructor(
    override val viewState: CardsState,
    override var validator: Validator?,
) : HiltBaseViewModel<ICards.State>(), ICards.ViewModel, IValidator {

}