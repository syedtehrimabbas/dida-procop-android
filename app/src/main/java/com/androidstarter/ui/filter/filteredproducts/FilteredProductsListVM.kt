package com.androidstarter.ui.filter.filteredproducts

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
import com.androidstarter.ui.productslist.IProductList
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.Category
import me.gilo.woodroid.models.Product
import me.gilo.woodroid.models.filters.ProductCategoryFilter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FilteredProductsListVM @Inject constructor(
    override val viewState: FilteredProductsListState,
    val databaseHelper: DatabaseHelper,
    val woocommerce: Woocommerce
) : HiltBaseViewModel<IProductList.State>(), IProductList.ViewModel {
    private var filters = hashMapOf<String, String>()

    private val _filteredProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val filteredProducts: LiveData<List<Product>> = _filteredProducts

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        filters = extras?.getSerializable("filters") as HashMap<String, String>
        fetchProducts(filters)
    }

    private fun fetchProducts(filter: HashMap<String, String>) {
        loading(true)
        woocommerce.ProductRepository().filter(filter)
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            _filteredProducts.postValue(it.body())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }
}