package com.androidstarter.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.clickevents.setOnClick
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentSearchProductBinding
import com.androidstarter.ui.home.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import me.gilo.woodroid.models.Product
import javax.inject.Inject

@AndroidEntryPoint
class SearchProductFragment :
    BaseNavViewModelFragment<FragmentSearchProductBinding, ISearchProduct.State, SearchProductVM>(),
    SearchView.OnQueryTextListener {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: SearchProductVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_search_product
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = ""
    override fun onClick(id: Int) {}
    override fun hasOptionMenu(): Boolean = true


    @Inject
    lateinit var searchProductAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewDataBinding.recyclerView.adapter = searchProductAdapter

        mViewDataBinding.searchBar.setOnQueryTextListener(this@SearchProductFragment)

        viewModel.searchProducts.observe(viewLifecycleOwner) {
            searchProductAdapter.setList(it)
        }

        searchProductAdapter.onItemClickListener = productClickListener
        searchProductAdapter.onChildItemClickListener = addToCartClickListener
    }

    private val addToCartClickListener = { view: View, position: Int, data: Product ->
        when (view.id) {
            R.id.addToCartBtn -> {
                viewModel.databaseHelper.addToCart(data)
            }
            R.id.addToFavBtn -> {

            }
        }
    }

    private val productClickListener = { view: View, position: Int, data: Product? ->
        arguments?.putInt("id", data?.id ?: 0)
        navigate(R.id.productDetailsFragment, arguments)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.searchProduct(it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
//        newText?.let { viewModel.searchProduct(it) }
        return false
    }
}