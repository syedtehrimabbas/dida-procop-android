package com.androidstarter.ui.cart

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class CartState @Inject constructor() : BaseState(), ICart.State {
    override var totalAmount: MutableLiveData<String> = MutableLiveData("0.0")
}