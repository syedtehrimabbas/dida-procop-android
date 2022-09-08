package com.androidstarter.ui.ordersuccess

import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderSuccessVM @Inject constructor(
    override val viewState: OrderSuccessState
) : HiltBaseViewModel<IOrderSuccess.State>(), IOrderSuccess.ViewModel {

}