package com.androidstarter.ui.fvrt

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.data.cart.models.FavouriteProduct
import com.androidstarter.databinding.FragmentFavouriteBinding
import com.androidstarter.ui.home.adapter.FavouriteProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment :
    BaseNavViewModelFragment<FragmentFavouriteBinding, IFavourite.State, FavouriteVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: FavouriteVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_favourite
    override fun toolBarVisibility(): Boolean = false
    override fun onClick(id: Int) {
        when (id) {
            R.id.closeBtn -> navigateBack()
        }
    }

    @Inject
    lateinit var favouriteAdapter: FavouriteProductsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.recyclerView.adapter = favouriteAdapter
        favouriteAdapter.onItemClickListener = productClickListener
        favouriteAdapter.onChildItemClickListener = addToCartClickListener

        viewModel.favouriteProducts.observe(viewLifecycleOwner) {
            favouriteAdapter.setList(it)
        }
    }

    private val productClickListener = { view: View, position: Int, data: FavouriteProduct? ->
        arguments?.putInt("id", data?.productId ?: 0)
        navigate(R.id.action_homeFragment_to_productDetailsFragment, arguments)
    }

    private val addToCartClickListener = { view: View, position: Int, data: FavouriteProduct ->
        when (view.id) {
            R.id.addToCartBtn -> viewModel.databaseHelper.addToCart(data)
            R.id.addToFavBtn -> {
                viewModel.databaseHelper.unFavouriteProduct(
                    data
                )
                if (favouriteAdapter.openList.isEmpty()) navigateBack()
            }
        }
    }
}