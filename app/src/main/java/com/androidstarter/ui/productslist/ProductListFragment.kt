package com.androidstarter.ui.productslist

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
import com.androidstarter.databinding.FragmentProductsListBinding
import com.androidstarter.ui.home.adapter.ProductCategoriesAdapter
import com.androidstarter.ui.home.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import me.gilo.woodroid.models.Category
import me.gilo.woodroid.models.Product
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment :
    BaseNavViewModelFragment<FragmentProductsListBinding, IProductList.State, ProductsListVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: ProductsListVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_products_list
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = ""
    override fun onClick(id: Int) {}
    override fun hasOptionMenu(): Boolean = true

    private val productCategoriesAdapter: ProductCategoriesAdapter = ProductCategoriesAdapter()

    private val childCategoriesAdapter: ProductCategoriesAdapter =
        ProductCategoriesAdapter(ProductCategoriesAdapter.TYPE_CATEGORY_BOX)

    @Inject
    lateinit var productAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productCategoriesAdapter.onItemClickListener = categoryClickListener
        childCategoriesAdapter.onItemClickListener = childCategoryClickListener

        viewModel.categoriesList.observe(viewLifecycleOwner) {
            productCategoriesAdapter.selectedPosition = viewModel.position.value ?: -1
            productCategoriesAdapter.setList(it)
        }
        viewModel.selectedCategory.observe(viewLifecycleOwner) {
            viewModel.fetchChildCategories(it)
        }

        viewModel.childCategories.observe(viewLifecycleOwner) {
            childCategoriesAdapter.setList(it)
        }

        viewModel.categoryProducts.observe(viewLifecycleOwner) {
            mViewDataBinding.catRecyclerView.visibility = View.GONE
            mViewDataBinding.recyclerView.adapter = productAdapter
            productAdapter.setList(it)
        }

        mViewDataBinding.catRecyclerView.adapter = productCategoriesAdapter
        mViewDataBinding.recyclerView.adapter = childCategoriesAdapter

        productAdapter.onItemClickListener = productClickListener
        productAdapter.onChildItemClickListener = addToCartClickListener

        mViewDataBinding.searchBar.setOnClickListener {
            navigate(R.id.action_productListFragment_to_searchProductFragment)
        }

    }

    private val categoryClickListener = { view: View, position: Int, data: Category? ->
        if (data !== null)
            viewModel.selectedCategory.value = data
    }

    private val childCategoryClickListener = { view: View, position: Int, data: Category? ->
        arguments?.putParcelable("category", data)
        navigateWithPopup(R.id.action_productListFragment_self,R.id.productListFragment, arguments)
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
            viewModel.databaseHelper.cartCount.value?.let {
                if (it > 0)
                    navigateToCart()
            }
        }

        favImage.setOnClick {

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