package com.androidstarter.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import com.dida.procop.BR
import com.dida.procop.R
import com.androidstarter.base.extensions.launchActivity
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.base.navgraph.host.NAVIGATION_Graph_ID
import com.androidstarter.base.navgraph.host.NAVIGATION_Graph_START_DESTINATION_ID
import com.androidstarter.base.navgraph.host.NavHostPresenterActivity
import com.dida.procop.databinding.FragmentHomeBinding
import com.androidstarter.ui.home.adapter.ProductCategoriesAdapter
import com.androidstarter.ui.home.adapter.ProductsAdapter
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import me.gilo.woodroid.models.Category
import me.gilo.woodroid.models.Product
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseNavViewModelFragment<FragmentHomeBinding, IHome.State, HomeVM>() {

    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: HomeVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_home
    override fun toolBarVisibility(): Boolean = false

    var adapter: ProductCategoriesAdapter = ProductCategoriesAdapter()

    @Inject
    lateinit var productAdapter: ProductsAdapter

    @Inject
    lateinit var gmundusedAdapter: ProductsAdapter

    @Inject
    lateinit var offsetProductsAdapter: ProductsAdapter

    @Inject
    lateinit var numericProductsAdapter: ProductsAdapter

    @Inject
    lateinit var nuancierProductsAdapter: ProductsAdapter

    @Inject
    lateinit var offresProductsAdapter: ProductsAdapter

    override fun onClick(id: Int) {
        when (id) {
            R.id.drawerButton -> mViewDataBinding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.closeBtn -> mViewDataBinding.drawerLayout.closeDrawer(GravityCompat.START)
            R.id.homeNav -> closeDrawer()
            R.id.settingNav -> {
                closeDrawer()
                navigate(R.id.action_homeFragment_to_settingsFragment)
            }
            R.id.faqNav -> {
                closeDrawer()
                navigate(R.id.action_homeFragment_to_faqFragment)
            }
            R.id.aboutNav -> {
                closeDrawer()
                navigate(R.id.action_homeFragment_to_aboutFragment)
            }

            R.id.contactNav -> {
                closeDrawer()
                navigate(R.id.action_homeFragment_to_contactUsFragment)
            }
            R.id.orderNav -> {
                closeDrawer()
                navigate(R.id.action_homeFragment_to_ordersFragment)
            }
            R.id.profileNav -> {
                closeDrawer()
                navigate(R.id.action_homeFragment_to_profileFragment)
            }
            R.id.logoutNav -> {
                closeDrawer()
                viewModel.endUserSession()
                launchActivity<NavHostPresenterActivity>(
                    options = Bundle(),
                    clearPrevious = true
                ) {
                    putExtra(NAVIGATION_Graph_ID, R.navigation.onboarding_nav_graph)
                    putExtra(
                        NAVIGATION_Graph_START_DESTINATION_ID,
                        R.id.loginFragment
                    )
                }
            }
            R.id.cartImage -> {
                viewModel.databaseHelper.cartCount.value?.let {
                    if (it > 0)
                        navigateToCart(viewModel.databaseHelper)
                }
            }
            R.id.favImage -> navigateToFavourite(viewModel.databaseHelper)
            R.id.filterBtn -> navigate(R.id.action_homeFragment_to_productFilterFragment)
        }
    }

    private fun closeDrawer() {
        mViewDataBinding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.categoriesList.observe(viewLifecycleOwner, ::setCategories)
        
        viewModel.colorProducts.observe(viewLifecycleOwner, ::set1802ColorProducts)
        viewModel.gmundusedProducts.observe(viewLifecycleOwner, ::setGmundusedProducts)
        viewModel.offsetProducts.observe(viewLifecycleOwner, ::setOffsetProducts)
        viewModel.numericProducts.observe(viewLifecycleOwner, ::setNumericProducts)
        viewModel.nuancierProducts.observe(viewLifecycleOwner, ::setNuancierProducts)
        viewModel.offresProducts.observe(viewLifecycleOwner, ::setOffresProducts)

        mViewDataBinding.drawerLayout
        mViewDataBinding.searchBar.setOnClickListener {
            navigate(R.id.action_homeFragment_to_searchProductFragment)
        }
    }

    private fun initRecyclerView() {
        with(mViewDataBinding) {

            catRecyclerView.adapter = adapter
            adapter.onItemClickListener = categoryClickListener

            
            colorProductsRV.adapter = productAdapter
            gmundusedRV.adapter = gmundusedAdapter
            offsetRV.adapter = offsetProductsAdapter
            numericRV.adapter = numericProductsAdapter
            nuancierRV.adapter = nuancierProductsAdapter
            offersRV.adapter = offresProductsAdapter

            productAdapter.onItemClickListener = productClickListener
            gmundusedAdapter.onItemClickListener = productClickListener
            offsetProductsAdapter.onItemClickListener = productClickListener
            numericProductsAdapter.onItemClickListener = productClickListener
            nuancierProductsAdapter.onItemClickListener = productClickListener
            offresProductsAdapter.onItemClickListener = productClickListener

            productAdapter.onChildItemClickListener = addToCartClickListener
            gmundusedAdapter.onChildItemClickListener = addToCartClickListener
            offsetProductsAdapter.onChildItemClickListener = addToCartClickListener
            numericProductsAdapter.onChildItemClickListener = addToCartClickListener
            nuancierProductsAdapter.onChildItemClickListener = addToCartClickListener
            offresProductsAdapter.onChildItemClickListener = addToCartClickListener

        }
    }

    private val productClickListener = { view: View, position: Int, data: Product? ->
        arguments?.putInt("id", data?.id ?: 0)
        navigate(R.id.action_homeFragment_to_productDetailsFragment, arguments)
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

    private val categoryClickListener = { view: View, position: Int, data: Category? ->
        arguments?.putParcelable("category", data)
        arguments?.putParcelableArrayList("categories", viewModel.categoriesList.value)
        arguments?.putInt("pos", position)
        navigate(R.id.action_homeFragment_to_productListFragment, arguments)
    }

    private fun setCategories(list: List<Category>) {
        adapter.setList(list)
    }

    private fun set1802ColorProducts(listProduct: List<Product>) {
        productAdapter.setList(listProduct)
    }

    private fun setGmundusedProducts(listProduct: List<Product>) {
        gmundusedAdapter.setList(listProduct)
    }

    private fun setOffsetProducts(listProduct: List<Product>) {
        offsetProductsAdapter.setList(listProduct)
    }

    private fun setNumericProducts(listProduct: List<Product>) {
        numericProductsAdapter.setList(listProduct)
    }
    private fun setNuancierProducts(listProduct: List<Product>) {
        nuancierProductsAdapter.setList(listProduct)
    }
    
    private fun setOffresProducts(listProduct: List<Product>) {
        offresProductsAdapter.setList(listProduct)
    }

    override fun onBackPressed(): Boolean {
        return if (mViewDataBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mViewDataBinding.drawerLayout.closeDrawer(GravityCompat.START)
            false
        } else super.onBackPressed()
    }
}