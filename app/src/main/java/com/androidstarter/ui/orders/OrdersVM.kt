package com.androidstarter.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.Category
import me.gilo.woodroid.models.Order
import me.gilo.woodroid.models.filters.OrderFilter
import me.gilo.woodroid.models.filters.ProductCategoryFilter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OrdersVM @Inject constructor(
    override val viewState: OrdersState,
    override var validator: Validator?,
    val databaseHelper: DatabaseHelper,
    val woocommerce: Woocommerce
) : HiltBaseViewModel<IOrders.State>(), IOrders.ViewModel, IValidator {

    private val _orders: MutableLiveData<ArrayList<Order>> = MutableLiveData()
    val orders: LiveData<ArrayList<Order>> = _orders

    override fun onResume() {
        super.onResume()
        fetchOrders(0)
    }

    private fun fetchOrders(customerId: Int) {
        val filter = OrderFilter()
        filter.setCustomer(customerId)

        loading(true)
        woocommerce.OrderRepository().orders(filter)
            .enqueue(object : Callback<List<Order>> {
                override fun onResponse(
                    call: Call<List<Order>>,
                    response: Response<List<Order>>
                ) {
                    response.let {
                        if (it.isSuccessful) {
                            loading(false)
                            val list = it.body()
                                _orders.postValue(list as ArrayList<Order>?)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    t.message?.let { loading(false, it) }
                }
            })
    }
}