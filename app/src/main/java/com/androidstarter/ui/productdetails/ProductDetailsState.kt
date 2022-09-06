package com.androidstarter.ui.productdetails

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import me.gilo.woodroid.models.Product
import javax.inject.Inject

class ProductDetailsState @Inject constructor() : BaseState(), IProductDetails.State {
    override var product: MutableLiveData<Product> = MutableLiveData()
}