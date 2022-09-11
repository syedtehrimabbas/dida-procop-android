package com.androidstarter.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.Product
import me.gilo.woodroid.models.filters.ProductFilter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchProductVM @Inject constructor(
    override val viewState: SearchProductState,
    val woocommerce: Woocommerce,
    val databaseHelper: DatabaseHelper
) : HiltBaseViewModel<ISearchProduct.State>(), ISearchProduct.ViewModel {
    private val _searchProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val searchProducts: LiveData<List<Product>> = _searchProducts

    fun searchProduct(query: String) {
        loading(true)
        val filters = ProductFilter()
        filters.setSearch(query)
//        filters.setPer_page(100)

        woocommerce.ProductRepository().products(filters)
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            _searchProducts.postValue(it.body())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    t.message?.let {
                        loading(true, it)
                    }
                }
            })
    }
}