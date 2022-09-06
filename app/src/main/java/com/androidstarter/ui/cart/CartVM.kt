package com.androidstarter.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.cart.models.CartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartVM @Inject constructor(
    override val viewState: CartState,
    override var validator: Validator?,
    val cartProductDao: CartProductDao
) : HiltBaseViewModel<ICart.State>(), ICart.ViewModel, IValidator {

    private val _cartProducts: MutableLiveData<List<CartProduct>> = MutableLiveData()
    val cartProducts: LiveData<List<CartProduct>> = _cartProducts

    init {
        loadCartItems()
    }

    override fun loadCartItems() {
        launch(Dispatcher.LongOperation) {
            _cartProducts.postValue(cartProductDao.getCartProducts())
        }
    }

    override fun updateItemIntoCart(product: CartProduct) {
        launch(Dispatcher.Background) {
            cartProductDao.updateProductFromCart(product)
            calculateTotalCartPrice(cartProductDao.getCartProducts())
        }
    }

    fun calculateTotalCartPrice(list: List<CartProduct>) {
        val amount = String.format("%.2f", list.sumOf { it.unitPrice * it.quantity }).toDouble()
        viewState.totalAmount.postValue(amount)
    }
}