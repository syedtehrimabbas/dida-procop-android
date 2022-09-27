package com.androidstarter.ui.cart.cards

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.ApiResponse
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.model.LineItem
import com.androidstarter.data.model.OrderRequest
import com.androidstarter.data.model.ShippingLine
import com.androidstarter.data.sessions.SessionManager
import com.androidstarter.data.source.remote.IAuthRepository
import com.androidstarter.ui.cart.billing.data.BillingAddressData
import com.androidstarter.ui.home.DatabaseHelper
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import javax.inject.Inject

@HiltViewModel
class CardsVM @Inject constructor(
    override val viewState: CardsState,
    override var validator: Validator?,
    val sessionManager: SessionManager,
    val cartProductDao: CartProductDao,
    val woocommerce: Woocommerce,
    val databaseHelper: DatabaseHelper,
    private val repository: IAuthRepository,
) : HiltBaseViewModel<ICards.State>(), ICards.ViewModel, IValidator {
    private var billingAddressData: BillingAddressData? = null
    private var shippingAddress: BillingAddressData? = null
    val amount: MutableLiveData<String> = MutableLiveData()
    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        billingAddressData = extras?.getParcelable("billingAddress")
        shippingAddress = billingAddressData
    }

    init {
        launch {
            amount.postValue(
                String.format(
                    "%.2f",
                    cartProductDao.getCartProducts().sumOf { it.unitPrice * it.quantity })
            )
        }
    }

    fun doOrder() {
        launch {
            val lineItems: ArrayList<LineItem> = arrayListOf()
            val products = cartProductDao.getCartProducts()

            products.forEach { cartProduct ->
                with(cartProduct) {
                    lineItems.add(
                        LineItem(
                            productId = productId,
                            quantity = quantity,
                            metaDate = metaDate,

                            )
                    )
                }
            }

            val orderRequest = OrderRequest(
                customerId = sessionManager.getUserId(),
                billing = billingAddressData,
                shipping = billingAddressData,
                lineItems = lineItems,
                shippingLines = arrayListOf(
                    ShippingLine(
                        total = String.format(
                            "%.2f",
                            products.sumOf { it.unitPrice * it.quantity })
                    )
                ),
            )
//            Log.d("ORDER_REQUEST", Gson().toJson(orderRequest))
            loading(true)
            when (val response = repository.createOrder(orderRequest)
            ) {
                is ApiResponse.Success -> {
                    loading(false)
                    databaseHelper.truncateCart()
                    clickEvent?.postValue(200)
                }
                is ApiResponse.Error -> {

                    databaseHelper.truncateCart()
                    clickEvent?.postValue(200)

                    loading(false)
                }
            }
        }
    }
}