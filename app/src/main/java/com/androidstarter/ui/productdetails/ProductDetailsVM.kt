package com.androidstarter.ui.productdetails

import android.content.Context
import android.os.Bundle
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
import com.dida.procop.R
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.Product
import me.gilo.woodroid.models.ProductAttribute
import me.gilo.woodroid.models.Variation
import me.gilo.woodroid.models.filters.ProductVariationFilter
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
                            viewState.productPrice.postValue("€ ${response.body()?.price}")
                        }
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

    override fun pVariations(id: Int) {
        val filter = ProductVariationFilter()
        filter.parent = intArrayOf(0)
        filter.setPer_page(100)

        woocommerce.VariationRepository().variations(id, filter)
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

    fun setVariationPrice(context: Context) {
        val productAttributes = viewState.product.value?.productAttributes?.filter { it.isVariation }
        val variations = viewState.variations.value
            ?.map {
                LightVariation(
                    productAttributes = it.attributes,
                    price = it.price
                )
            }
        if (productAttributes == null || variations == null) {
            viewState.product.value?.price = 0.0.toString()
            viewState.productPrice.postValue(context.getString(R.string.price_unavailable))
            return
        }

        val matchingVariation = variations.find { variation ->
            productAttributes.any { attribute ->
                variation.productAttributes.any { varAttribute ->
                    attribute.name == varAttribute.name && attribute.selectedAttribute == varAttribute.option
                }
            }
        }
        if (matchingVariation == null) {
            viewState.product.value?.price = 0.0.toString()
            viewState.productPrice.postValue(context.getString(R.string.price_unavailable))
        } else {
            val price: Double = matchingVariation.price
            viewState.product.value?.price = price.toString()
            viewState.productPrice.postValue("€ $price")
        }
    }

    data class LightVariation(
        var productAttributes: ArrayList<ProductAttribute>,
        var price: Double
    )
}