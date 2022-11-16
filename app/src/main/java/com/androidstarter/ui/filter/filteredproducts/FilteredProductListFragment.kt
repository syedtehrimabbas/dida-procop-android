package com.androidstarter.ui.filter.filteredproducts

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.clickevents.setOnClick
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentFilteredProductsListBinding
import com.androidstarter.databinding.FragmentProductsListBinding
import com.androidstarter.ui.home.adapter.ProductCategoriesAdapter
import com.androidstarter.ui.home.adapter.ProductsAdapter
import com.androidstarter.ui.productslist.IProductList
import dagger.hilt.android.AndroidEntryPoint
import me.gilo.woodroid.models.Category
import me.gilo.woodroid.models.Product
import javax.inject.Inject

@AndroidEntryPoint
class FilteredProductListFragment :
    BaseNavViewModelFragment<FragmentFilteredProductsListBinding, IProductList.State, FilteredProductsListVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: FilteredProductsListVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_filtered_products_list
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = ""
    override fun onClick(id: Int) {}
    override fun hasOptionMenu(): Boolean = true

    @Inject
    lateinit var productAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.recyclerView.adapter = productAdapter
        viewModel.filteredProducts.observe(viewLifecycleOwner) {
            productAdapter.setList(it)
        }


        productAdapter.onItemClickListener = productClickListener
        productAdapter.onChildItemClickListener = addToCartClickListener

    }

    private val productClickListener = { view: View, position: Int, data: Product? ->
        arguments?.putInt("id", data?.id ?: 0)
        navigate(R.id.productDetailsFragment, arguments)
    }

    private val addToCartClickListener = { view: View, position: Int, data: Product ->
        when (view.id) {
            R.id.addToCartBtn -> {
                viewModel.databaseHelper.addToCart(data)
            }
            R.id.addToFavBtn -> {
                viewModel.databaseHelper.addToFav(data)
            }
        }
    }

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

}