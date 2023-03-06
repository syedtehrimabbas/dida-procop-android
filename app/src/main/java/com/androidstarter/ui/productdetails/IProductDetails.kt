package com.androidstarter.ui.productdetails

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase
import me.gilo.woodroid.models.Product
import me.gilo.woodroid.models.Variation

interface IProductDetails {
    interface State : IBase.State {
        var product: MutableLiveData<Product>
        val maxPrice: MutableLiveData<String>
        val minPrice: MutableLiveData<String>
        val productPrice: MutableLiveData<String>
        val variations: MutableLiveData<List<Variation>>
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun pDetails(id: Int)
        fun pVariations(id: Int)
    }
}