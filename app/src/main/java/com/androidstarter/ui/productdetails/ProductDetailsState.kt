package com.androidstarter.ui.productdetails

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import me.gilo.woodroid.models.Product
import me.gilo.woodroid.models.Variation
import javax.inject.Inject

class ProductDetailsState @Inject constructor() : BaseState(), IProductDetails.State {
    override var product: MutableLiveData<Product> = MutableLiveData()
    override val minPrice: MutableLiveData<String> = MutableLiveData("")
    override val maxPrice: MutableLiveData<String> = MutableLiveData("")
    override val variations: MutableLiveData<List<Variation>> =
        MutableLiveData(listOf())
}