package com.androidstarter.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.sessions.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.Category
import me.gilo.woodroid.models.Product
import me.gilo.woodroid.models.filters.ProductCategoryFilter
import me.gilo.woodroid.models.filters.ProductFilter
import me.gilo.woodroid.models.filters.Sort
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    override val viewState: HomeState,
    private val woocommerce: Woocommerce,
    val sessionManager: SessionManager,
    @ApplicationContext val context: Context,
    val databaseHelper: DatabaseHelper
) : HiltBaseViewModel<IHome.State>(), IHome.ViewModel {
    private val _categoriesList: MutableLiveData<MutableList<Category>> = MutableLiveData()
    override val categoriesList: LiveData<MutableList<Category>> = _categoriesList

    private val _currentOfferProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val currentOfferProducts: LiveData<List<Product>> = _currentOfferProducts

    private val _procopExclusiveProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val procopExclusiveProducts: LiveData<List<Product>> = _procopExclusiveProducts

    private val _marquesEnTendanceProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val marquesEnTendanceProducts: LiveData<List<Product>> = _marquesEnTendanceProducts

    init {
        fetchCategories()
        fetchOnSaleProducts()
        fetchProcopExclusive()
        fetchMarquesEnTendance()
        databaseHelper.cartCount()
        sessionManager.setUser()
    }

    override fun fetchCategories() {
        loading(true)
        val filter = ProductCategoryFilter()
        filter.parent = intArrayOf(0)
        filter.setPer_page(100)
        woocommerce.CategoryRepository().categories(filter)
            .enqueue(object : Callback<List<Category>> {
                override fun onResponse(
                    call: Call<List<Category>>,
                    response: Response<List<Category>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            val list = mutableListOf<Category>()
                            val allCategory = Category()
                            allCategory.id = 1500
                            allCategory.name = "All"
                            allCategory.display = "All"
                            list.add(allCategory)
                            (it.body() as MutableList<Category>?)?.let { it1 -> list.addAll(it1) }
                            _categoriesList.postValue(list)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

    override fun fetchOnSaleProducts() {
//        loading(true)
        val filters = ProductFilter()
        filters.setOrderby("date")

        woocommerce.ProductRepository().productByCategory(15)
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            _currentOfferProducts.postValue(it.body())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

    override fun fetchProcopExclusive() {
//        loading(true)
        val filters = ProductFilter()
        filters.setOrderby("menu_order")

        woocommerce.ProductRepository().products(filters)
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            _procopExclusiveProducts.postValue(it.body())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

    override fun fetchMarquesEnTendance() {
//        loading(true)
        val filters = ProductFilter()
        filters.setOrder("date_created")
        filters.setOrder(Sort.ASCENDING)

        woocommerce.ProductRepository().products(filters)
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            _marquesEnTendanceProducts.postValue(it.body())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

    fun endUserSession() {
        sessionManager.endUserSession()
        databaseHelper.truncateProductTable()
    }
}