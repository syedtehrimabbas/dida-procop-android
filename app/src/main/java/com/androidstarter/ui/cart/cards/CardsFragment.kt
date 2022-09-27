package com.androidstarter.ui.cart.cards

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentCardsBinding
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment :
    BaseNavViewModelFragment<FragmentCardsBinding, ICards.State, CardsVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: CardsVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_cards
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = "Cards"
    override fun onClick(id: Int) {
        when (id) {
            R.id.cardPayment -> viewState.selected.value = 1
            R.id.paypalPayment -> viewState.selected.value = 2
            200 -> navigateWithPopup(
                R.id.action_cardsFragment_to_orderSuccessFragment,
                R.id.cartFragment
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.amount.observe(viewLifecycleOwner) {
            mViewDataBinding.paymentButtonContainer.setup(
                createOrder =
                CreateOrder { createOrderActions ->
                    val order =
                        Order(
                            intent = OrderIntent.CAPTURE,
                            appContext = AppContext(userAction = UserAction.PAY_NOW),
                            purchaseUnitList =
                            listOf(
                                PurchaseUnit(
                                    amount =
                                    Amount(
                                        currencyCode = CurrencyCode.EUR,
                                        value = viewModel.amount.value.toString()
                                    ),
                                )
                            )
                        )
                    createOrderActions.create(order)
                },
                onApprove =
                OnApprove { approval ->
                    approval.orderActions.capture { captureOrderResult ->
                        viewModel.doOrder()
                        Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
                    }
                },
                onCancel = OnCancel {
                    showToast("Buyer canceled the PayPal experience")
                    Log.d("OnCancel", "Buyer canceled the PayPal experience.")
                },
                onError = OnError { errorInfo ->
//                    showToast(errorInfo.reason)
                    Log.d("OnError", "Error: $errorInfo")
//                    viewModel.doOrder()
                }
            )
        }
    }
}