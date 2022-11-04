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
    private val _categoriesList: MutableLiveData<ArrayList<Category>> = MutableLiveData()
    override val categoriesList: LiveData<ArrayList<Category>> = _categoriesList

    private val _1802ColorProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val colorProducts: LiveData<List<Product>> = _1802ColorProducts

    private val _gmundusedProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val gmundusedProducts: LiveData<List<Product>> = _gmundusedProducts

    private val _offsetProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val offsetProducts: LiveData<List<Product>> = _offsetProducts

    private val _numericProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val numericProducts: LiveData<List<Product>> = _numericProducts

    private val _nuancierProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val nuancierProducts: LiveData<List<Product>> = _nuancierProducts

    private val _offresProducts: MutableLiveData<List<Product>> = MutableLiveData()
    override val offresProducts: LiveData<List<Product>> = _offresProducts

    init {
        fetchCategories()
        fetchProductByCategories(1141) // 1802 1141
        fetchProductByCategories(2639) // Gmundused 2639
        fetchProductByCategories(986) // Offset 986
        fetchProductByCategories(1231) // Numeric  1231
        fetchProductByCategories(1723) // Nuancier 1723
        fetchProductByCategories(15) // Offres 15

        databaseHelper.cartCount()
        databaseHelper.favouriteCount()
        sessionManager.setUser()
    }

    override fun onResume() {
        super.onResume()
        databaseHelper.cartCount()
        databaseHelper.favouriteCount()
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
                            allCategory.display = "All Products"
                            list.add(allCategory)
                            (it.body() as MutableList<Category>?)?.let { it1 -> list.addAll(it1) }
                            _categoriesList.postValue(list as ArrayList<Category>?)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

    override fun fetchProductByCategories(catId: Int) {
        woocommerce.ProductRepository().productByCategory(catId)
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    response.let {
                        if (it.isSuccessful) {
                            when (catId) {
                                1141 -> _1802ColorProducts.postValue(it.body())
                                2639 -> _gmundusedProducts.postValue(it.body())
                                986 -> _offsetProducts.postValue(it.body())
                                1231 -> _numericProducts.postValue(it.body())
                                1723 -> _nuancierProducts.postValue(it.body())
                                15 -> {
                                    _offresProducts.postValue(it.body())
                                    loading(false)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    fetchProductByCategories(catId)
                    t.message?.let { loading(false, it) }
                }
            })
    }

//    override fun fetchProcopExclusive() {
////        loading(true)
////        val filters = ProductFilter()
////        filters.setOrderby("menu_order")
//
//        woocommerce.ProductRepository().productByCategory(2639)
//            .enqueue(object : Callback<List<Product>> {
//                override fun onResponse(
//                    call: Call<List<Product>>,
//                    response: Response<List<Product>>
//                ) {
//                    loading(false)
//                    response.let {
//                        if (it.isSuccessful) {
//                            _gmundusedProducts.postValue(it.body())
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
//                    t.message?.let { loading(false, it) }
//                }
//            })
//    }
//
//    override fun fetchMarquesEnTendance() {
////        loading(true)
//        val filters = ProductFilter()
//        filters.setOrder("date_created")
//        filters.setOrder(Sort.ASCENDING)
//
//        woocommerce.ProductRepository().products(filters)
//            .enqueue(object : Callback<List<Product>> {
//                override fun onResponse(
//                    call: Call<List<Product>>,
//                    response: Response<List<Product>>
//                ) {
//                    loading(false)
//                    response.let {
//                        if (it.isSuccessful) {
//                            _offsetProducts.postValue(it.body())
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
//                    t.message?.let { loading(false, it) }
//                }
//            })
//    }

    fun endUserSession() {
        sessionManager.endUserSession()
        databaseHelper.truncateProductTable()
    }
}