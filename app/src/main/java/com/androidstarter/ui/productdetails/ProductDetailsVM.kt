package com.androidstarter.ui.productdetails

import android.os.Bundle
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductDetailsVM @Inject constructor(
    override val viewState: ProductDetailsState,
    private val woocommerce: Woocommerce,
    val cartProductDao: CartProductDao
) : HiltBaseViewModel<IProductDetails.State>(), IProductDetails.ViewModel {
    val databaseHelper = DatabaseHelper(cartProductDao)
    var isInCart = false
    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        val id = extras?.getInt("id") ?: 0
        pDetails(id)
    }

    override fun pDetails(id: Int) {
        loading(true)
        woocommerce.ProductRepository().product(id)
            .enqueue(object : Callback<Product> {
                override fun onResponse(
                    call: Call<Product>,
                    response: Response<Product>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            viewState.product.postValue(response.body())
                        }
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    t.message?.let { loading(true, it) }
                }
            })
    }
}