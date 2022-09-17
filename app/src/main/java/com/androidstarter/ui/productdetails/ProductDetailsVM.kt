package com.androidstarter.ui.productdetails

import android.os.Bundle
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.Product
import me.gilo.woodroid.models.Variation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductDetailsVM @Inject constructor(
    override val viewState: ProductDetailsState,
    private val woocommerce: Woocommerce,
    val databaseHelper: DatabaseHelper
) : HiltBaseViewModel<IProductDetails.State>(), IProductDetails.ViewModel {
    var isInCart = false
    var isFav = false
    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        val id = extras?.getInt("id") ?: 0
        pDetails(id)
        pVariations(id)
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
                    t.message?.let { loading(false, it) }
                }
            })
    }

    override fun pVariations(id: Int) {
        woocommerce.VariationRepository().variations(id)
            .enqueue(object : Callback<List<Variation>> {
                override fun onResponse(
                    call: Call<List<Variation>>,
                    response: Response<List<Variation>>
                ) {
                    response.let {
                        if (it.isSuccessful) {
                            response.body().let { variationsList ->
                                with(viewState) {
                                    variations.postValue(variationsList)
                                    val minVariation =
                                        variationsList?.minByOrNull { it -> it.price }
                                    val maxVariation =
                                        variationsList?.maxByOrNull { it -> it.price }
                                    minVariation?.let { it ->
                                        minPrice.postValue(it.price.toString())
                                    }
                                    maxVariation?.let { it ->
                                        maxPrice.postValue(it.price.toString())
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<Variation>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }
}