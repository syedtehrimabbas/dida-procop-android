package com.androidstarter.ui.cart

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.data.cart.models.CartProduct
import com.androidstarter.databinding.FragmentCartBinding
import com.androidstarter.ui.cart.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
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
            R.id.checkoutButton -> navigate(R.id.action_cartFragment_to_billingAddressFragment)
        }
    }

    @Inject
    lateinit var cartAdapter: CartAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Take action for the swiped item
                val position = viewHolder.adapterPosition
                val data = cartAdapter.getItem(position)
                cartAdapter.removeItem(position)
                viewModel._cartProducts.value?.removeAt(position)
                viewModel.launch(Dispatcher.Background) {
                    viewModel.cartProductDao.deleteProductFromCart(data.productId)
                }
                viewModel._cartProducts.value?.let { viewModel.calculateTotalCartPrice(it) }
                if (viewModel._cartProducts.value?.isEmpty() == true) navigateBack()
            }

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.appPink
                        )
                    ).addActionIcon(R.drawable.ic_fluent_delete).create().decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(mViewDataBinding.cartRecyclerView)

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