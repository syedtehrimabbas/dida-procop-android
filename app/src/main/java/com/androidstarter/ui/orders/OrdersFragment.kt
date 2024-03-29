package com.androidstarter.ui.orders

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.dida.procop.BR
import com.dida.procop.R
import com.androidstarter.base.clickevents.setOnClick
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.dida.procop.databinding.FragmentOrdersBinding
import com.androidstarter.ui.orders.adapter.OrdersAdapterAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment :
    BaseNavViewModelFragment<FragmentOrdersBinding, IOrders.State, OrdersVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: OrdersVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_orders
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = getString(R.string.screen_order_my_orders_button_title)

    @Inject
    lateinit var ordersAdapterAdapter: OrdersAdapterAdapter

    override fun onClick(id: Int) {
        when (id) {
            R.id.orderNow -> navigateBack()
        }
    }

    override fun hasOptionMenu(): Boolean = true
    override fun onPrepareOptionsMenu(menu: Menu) {

        val menuItem = menu.findItem(R.id.itemCartMenu)
        val cartLayout = menuItem.actionView as ConstraintLayout
        val cartButton = cartLayout.findViewById<ImageView>(R.id.cartImage)
        val cartCount = cartLayout.findViewById<TextView>(R.id.cartCount)

        val favImage = cartLayout.findViewById<ImageView>(R.id.favImage)
        val favCount = cartLayout.findViewById<TextView>(R.id.favCount)

        cartCount.text = viewModel.databaseHelper.cartCount.value.toString()
        favCount.text = viewModel.databaseHelper.favCount.value.toString()

        cartButton.setOnClick {
            navigateToCart(viewModel.databaseHelper)
        }

        favImage.setOnClick {
            navigateToFavourite(viewModel.databaseHelper)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.databaseHelper.cartCount()
        viewModel.databaseHelper.favouriteCount()
    }

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.databaseHelper.cartCount.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }
        viewModel.databaseHelper.favCount.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.recyclerView.adapter = ordersAdapterAdapter

        viewModel.orders.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                mViewDataBinding.recyclerView.visibility = View.GONE
                mViewDataBinding.noOrderLayout.visibility = View.VISIBLE
            } else {
                mViewDataBinding.recyclerView.visibility = View.VISIBLE
                mViewDataBinding.noOrderLayout.visibility = View.GONE
                ordersAdapterAdapter.setList(it)
            }
        }
    }
}