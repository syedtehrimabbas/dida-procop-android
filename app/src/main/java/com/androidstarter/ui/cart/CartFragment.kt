package com.androidstarter.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.data.cart.models.CartProduct
import com.androidstarter.databinding.FragmentCartBinding
import com.androidstarter.ui.cart.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment :
    BaseNavViewModelFragment<FragmentCartBinding, ICart.State, CartVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: CartVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_cart
    override fun toolBarVisibility(): Boolean = false
    override fun onClick(id: Int) {
        when (id) {
            R.id.closeBtn -> navigateBack()
            R.id.checkoutButton -> navigate(R.id.billingAddressFragment)
        }
    }

    @Inject
    lateinit var cartAdapter: CartAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.cartRecyclerView.adapter = cartAdapter
        cartAdapter.onChildItemClickListener = cartQuantityHandler
        viewModel.cartProducts.observe(viewLifecycleOwner) {
            cartAdapter.setList(it)
            viewModel.calculateTotalCartPrice(it)
        }
    }

    private val cartQuantityHandler = { view: View, _: Int, data: CartProduct ->
        when (view.id) {
            R.id.plusCart -> {
                viewModel.updateItemIntoCart(data)
            }
            R.id.minusCart -> {
                viewModel.updateItemIntoCart(data)
            }
        }
    }

}