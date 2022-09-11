package com.androidstarter.ui.productslist

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
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
class ProductsListVM @Inject constructor(
    override val viewState: ProductListState,
    val databaseHelper: DatabaseHelper,
    val woocommerce: Woocommerce
) : HiltBaseViewModel<IProductList.State>(), IProductList.ViewModel {

    private val _categoriesList: MutableLiveData<ArrayList<Category>> = MutableLiveData()
    val categoriesList: LiveData<ArrayList<Category>> = _categoriesList
    val position: MutableLiveData<Int> = MutableLiveData(-1)
    val selectedCategory: MutableLiveData<Category> = MutableLiveData()

    private val _childCategories: MutableLiveData<ArrayList<Category>> = MutableLiveData()
    val childCategories: LiveData<ArrayList<Category>> = _childCategories

    private val _categoryProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val categoryProducts: LiveData<List<Product>> = _categoryProducts

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)

        _categoriesList.value = extras?.getParcelableArrayList("categories")

        selectedCategory.value = extras?.getParcelable("category")

        position.value = extras?.getInt("pos")
    }

    fun fetchChildCategories(category: Category) {
        if ((category.name == "All")) {
            val withoutAll = categoriesList.value
            withoutAll?.removeAt(0)
            _childCategories.value = withoutAll
        } else {
            loading(true)
            val filter = ProductCategoryFilter()
            filter.parent = intArrayOf(category.id)
            filter.setPer_page(100)
            woocommerce.CategoryRepository().categories(filter)
                .enqueue(object : Callback<List<Category>> {
                    override fun onResponse(
                        call: Call<List<Category>>,
                        response: Response<List<Category>>
                    ) {
                        response.let {
                            if (it.isSuccessful) {
                                val list = it.body()
                                if (list?.isEmpty() == true) {
                                    fetchProducts(category)
                                } else {
                                    loading(false)
                                    _childCategories.postValue(list as java.util.ArrayList<Category>?)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        t.message?.let { loading(false, it) }
                    }
                })
        }
    }

    private fun fetchProducts(category: Category) {

        woocommerce.ProductRepository().productByCategory(category.id)
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            _categoryProducts.postValue(it.body())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }
}