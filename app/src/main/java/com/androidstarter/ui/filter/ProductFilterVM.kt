package com.androidstarter.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.AttributeTerm
import me.gilo.woodroid.models.Category
import me.gilo.woodroid.models.filters.ProductCategoryFilter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductFilterVM @Inject constructor(
    override val viewState: ProductFilterState,
    private val woocommerce: Woocommerce,
) : HiltBaseViewModel<IProductFilter.State>(), IProductFilter.ViewModel {
    private val _categoriesList: MutableLiveData<ArrayList<Category>> = MutableLiveData()
    override val categoriesList: LiveData<ArrayList<Category>> = _categoriesList

    private val _subCategoriesList: MutableLiveData<ArrayList<Category>> = MutableLiveData()
    override val subCategoriesList: LiveData<ArrayList<Category>> = _subCategoriesList

    private val _colors: MutableLiveData<ArrayList<FilterColor>> = MutableLiveData()
    val colors: LiveData<ArrayList<FilterColor>> = _colors

    private val _brands: MutableLiveData<ArrayList<AttributeTerm>> = MutableLiveData()
    val brands: LiveData<ArrayList<AttributeTerm>> = _brands

    private val _thickNess: MutableLiveData<ArrayList<AttributeTerm>> = MutableLiveData()
    val thickNess: LiveData<ArrayList<AttributeTerm>> = _thickNess
    private val _filters: HashMap<String, String> = hashMapOf()
    val filters: HashMap<String, String> = _filters

    init {
        fetchCategories()
        fetchBrandsAttrTerms()
        fetchThicknessAttrTerms()
        _colors.postValue(listOfColors())
    }

    private fun fetchBrandsAttrTerms() {
        if (ATTRIBUTE_BRANDS_ID != 18) alert("Brands id is changed and unable to load brands")

        loading(true)
        woocommerce.AttributeTermRepository().terms(ATTRIBUTE_BRANDS_ID)
            .enqueue(object : Callback<List<AttributeTerm>> {
                override fun onResponse(
                    call: Call<List<AttributeTerm>>, response: Response<List<AttributeTerm>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            _brands.postValue(it.body() as ArrayList<AttributeTerm>?)
                        }
                    }
                }

                override fun onFailure(call: Call<List<AttributeTerm>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

    private fun fetchThicknessAttrTerms() {

        loading(true)
        woocommerce.AttributeTermRepository().terms(ATTRIBUTE_THICKNESS_ID)
            .enqueue(object : Callback<List<AttributeTerm>> {
                override fun onResponse(
                    call: Call<List<AttributeTerm>>, response: Response<List<AttributeTerm>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            _thickNess.postValue(it.body() as ArrayList<AttributeTerm>?)
                        }
                    }
                }

                override fun onFailure(call: Call<List<AttributeTerm>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

    private fun fetchCategories() {
        loading(true)
        val filter = ProductCategoryFilter()
        filter.parent = intArrayOf(0)
        filter.setPer_page(100)
        woocommerce.CategoryRepository().categories(filter)
            .enqueue(object : Callback<List<Category>> {
                override fun onResponse(
                    call: Call<List<Category>>, response: Response<List<Category>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            val list = mutableListOf<Category>()
//                            val allCategory = Category()
//                            allCategory.id = 1500
//                            allCategory.name = "All"
//                            allCategory.display = "All Products"
//                            list.add(allCategory)
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

    fun fetchSubCategories(catId: Int) {
        loading(true)
        val filter = ProductCategoryFilter()
        filter.parent = intArrayOf(catId)
        filter.setPer_page(100)
        woocommerce.CategoryRepository().categories(filter)
            .enqueue(object : Callback<List<Category>> {
                override fun onResponse(
                    call: Call<List<Category>>, response: Response<List<Category>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            val list = it.body()
                            _subCategoriesList.postValue(list as ArrayList<Category>?)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

//    https://papiersprocop.com//wp-json/wc/v3/products?filter[pa_couleur]=1328&filter[pa_epaisseur]=2465&consumer_key=ck_c70e62bd7b8f6e2dce406a127f9ab6dd11d98d45&consumer_secret=cs_6bb3e7ca80db9d9a87514dd37fdee88dbbd22034
//    https://papiersprocop.com//wp-json/wc/v3/products?attribute=pa_couleur&attribute_term=1328&consumer_key=ck_c70e62bd7b8f6e2dce406a127f9ab6dd11d98d45&consumer_secret=cs_6bb3e7ca80db9d9a87514dd37fdee88dbbd22034

    private fun listOfColors() = arrayListOf(
        FilterColor(
            2498, "Bleu", "Blue", "#318ce7"
        ),
        FilterColor(
            1328, "Orange", "Orange", "#fc9303"
        ),
        FilterColor(
            1827, "Brun", "Brown", "#584d45"
        ),
        FilterColor(
            2010, "Noir", "black", "#000000"
        ),
        FilterColor(
            1315, "Blanc", "White", "#FFFFFF"
        ),
        FilterColor(
            1876, "Creme", "Cream", "#fcefc6"
        ),
        FilterColor(
            1818, "Vert", "Green", "#49b675"
        ),
        FilterColor(
            1308, "Or", "gold", "#cfc89f"
        ),
        FilterColor(
            1821, "Gris", "Grey", "#d3d3d1"
        ),
        FilterColor(
            1842, "Rouge", "Red", "#dd3333"
        ),
        FilterColor(
            1842, "Argent", "Silver", "#c0c0c0"
        ),
        FilterColor(
            1842, "Violet", "Purple", "#4f5a84"
        ),
        FilterColor(
            1842, "Jaune", "Yellow", "#eeee22"
        ),
        FilterColor(
            1842, "Rose", "Pink", "#c09da4"
        ),
        FilterColor(
            1842, "Anthracite", "Anthracite", "#878787"
        ),
        FilterColor(
            1842, "Beige", "Beige", "#f2e7bf"
        ),
        FilterColor(
            1842, "Marron", "Chestnut", "#6f3730"
        ),
        FilterColor(
            1842, "Rose Vif", "Hot pink", "#e8ddde"
        ),
        FilterColor(
            1842, "Taupe", "Mole", "#7d6b62"
        ),
    )

    fun updateFilter(key: String, value: String?) {
        _filters[key] = value ?: ""
    }

    fun removeFilter(key: String) {
        _filters.remove(key)
    }

    data class FilterColor(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("slug") val slug: String,
        @SerializedName("code") val colorCode: String = ""
    )

    companion object {
        const val ATTRIBUTE_BRANDS_ID = 18
        const val ATTRIBUTE_THICKNESS_ID = 23
    }
}