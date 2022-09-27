package com.androidstarter.ui.home

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.viewmodel.BaseCoroutineViewModel
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.cart.models.CartMetaData
import com.androidstarter.data.cart.models.CartProduct
import com.androidstarter.data.cart.models.FavouriteProduct
import me.gilo.woodroid.models.Product
import javax.inject.Inject

class DatabaseHelper @Inject constructor(
    private val cartProductDao: CartProductDao
) : BaseCoroutineViewModel() {
    val favCount: MutableLiveData<Int> = MutableLiveData(0)
    val cartCount: MutableLiveData<Int> = MutableLiveData(0)

    fun addToCart(product: Product) {
        launch(Dispatcher.Background) {
            val fetchProduct = cartProductDao.getProductById(product.id)
            if (fetchProduct != null) {
                cartProductDao.deleteProductFromCart(fetchProduct.productId)
            } else {
                val metaDate: ArrayList<CartMetaData> = arrayListOf()
                product.productAttributes.forEach { attribute ->
                    var value = ""
                    if (attribute.selectedAttribute.isNotEmpty()) {
                        value = attribute.selectedAttribute
                    } else {
                        attribute.options?.let {
                            if (it.size > 0)
                                value = it[0]
                        }
                    }
                    metaDate.add(
                        CartMetaData(
                            id = attribute.id,
                            displayKey = attribute.name ?: "",
                            displayValue = value ?: "",
                            key = attribute.name ?: "",
                            value = value ?: ""
                        )
                    )
                }
                val pojoProduct = CartProduct(
                    productId = product.id,
                    quantity = 1,
                    productName = product.name,
                    unitPrice = product.price.toDouble(),
                    productImage = product.getFeatureImage(),
                    metaDate = metaDate
                )
                cartProductDao.addProductToCart(pojoProduct)
            }
            cartCount()
        }
    }

    fun addToCart(product: FavouriteProduct) {
        launch(Dispatcher.Background) {
            val fetchProduct = cartProductDao.getProductById(product.productId)
            if (fetchProduct != null) {
                cartProductDao.deleteProductFromCart(fetchProduct.productId)
            } else {
                val metaDate: ArrayList<CartMetaData> = arrayListOf()

                val pojoProduct = CartProduct(
                    productId = product.productId,
                    quantity = 1,
                    productName = product.productName,
                    unitPrice = product.unitPrice,
                    productImage = product.productImage,
                    metaDate = metaDate
                )
                cartProductDao.addProductToCart(pojoProduct)
            }
            cartCount()
        }
    }

    fun addToFav(product: Product) {
        launch(Dispatcher.Background) {
            val fetchProduct = cartProductDao.getFavProductById(product.id)
            if (fetchProduct != null) {
                cartProductDao.deleteProductFromFav(fetchProduct.productId)
            } else {
                val metaDate: ArrayList<CartMetaData> = arrayListOf()
                product.productAttributes.forEach { attribute ->
                    var value = ""
                    if (attribute.selectedAttribute.isNotEmpty()) {
                        value = attribute.selectedAttribute
                    } else {
                        attribute.options?.let {
                            if (it.size > 0)
                                value = it[0]
                        }
                    }
                    metaDate.add(
                        CartMetaData(
                            id = attribute.id,
                            displayKey = attribute.name ?: "",
                            displayValue = value ?: "",
                            key = attribute.name ?: "",
                            value = value ?: ""
                        )
                    )
                }
                val pojoProduct = FavouriteProduct(
                    productId = product.id,
                    quantity = 1,
                    productName = product.name,
                    unitPrice = product.price.toDouble(),
                    productImage = product.getFeatureImage(),
                    metaDate = metaDate
                )
                cartProductDao.addToFav(pojoProduct)
            }
            favouriteCount()
        }
    }

    fun cartCount() = launch {
        cartCount.postValue(cartProductDao.cartCount())
    }

    fun favouriteCount() = launch {
        favCount.postValue(cartProductDao.favCount())
    }


    fun truncateProductTable() {
        launch(Dispatcher.Background) {
            cartProductDao.truncateProductTable()
            cartProductDao.truncateFavProductTable()
        }
    }
    fun truncateCart() {
        launch(Dispatcher.Background) {
            cartProductDao.truncateProductTable()
        }
    }

    fun getProductById(id: Int): CartProduct? = cartProductDao.getProductById(id)
    fun getFavProductById(id: Int): FavouriteProduct? = cartProductDao.getFavProductById(id)
    fun unFavouriteProduct(favouriteProduct: FavouriteProduct) = launch(Dispatcher.Background) {
        cartProductDao.deleteProductFromFav(favouriteProduct)
    }
}