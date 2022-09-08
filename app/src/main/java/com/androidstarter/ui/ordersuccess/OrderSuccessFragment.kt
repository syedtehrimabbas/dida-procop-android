package com.androidstarter.ui.ordersuccess

import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentContactUsBinding
import com.androidstarter.databinding.FragmentOrderSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderSuccessFragment :
    BaseNavViewModelFragment<FragmentOrderSuccessBinding, IOrderSuccess.State, OrderSuccessVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: OrderSuccessVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_order_success
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = "Confirm"
    override fun onClick(id: Int) {}
}