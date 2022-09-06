package com.androidstarter.ui.productdetails

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase
import me.gilo.woodroid.models.Product

interface IProductDetails {
    interface State : IBase.State {
        var product: MutableLiveData<Product>
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun pDetails(id: Int)
    }
}