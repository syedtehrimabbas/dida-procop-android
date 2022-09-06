package com.androidstarter.ui.cart

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase
import com.androidstarter.data.cart.models.CartProduct

interface ICart {
    interface State : IBase.State {
        var totalAmount: MutableLiveData<Double>
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun loadCartItems()
        fun updateItemIntoCart(product: CartProduct)
    }
}