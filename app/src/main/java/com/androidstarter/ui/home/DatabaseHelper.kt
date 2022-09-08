package com.androidstarter.ui.home

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.viewmodel.BaseCoroutineViewModel
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.cart.models.CartMetaData
import com.androidstarter.data.cart.models.CartProduct
import me.gilo.woodroid.models.Product

class DatabaseHelper constructor(
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
                    val value =
                        attribute.selectedAttribute.ifEmpty { attribute.options?.get(0) }
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

    fun cartCount() {
        launch {
            cartCount.postValue(cartProductDao.cartCount())
        }
    }
}