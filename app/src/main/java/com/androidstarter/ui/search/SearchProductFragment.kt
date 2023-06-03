package com.androidstarter.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.androidstarter.base.clickevents.setOnClick
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.ui.home.adapter.ProductsAdapter
import com.dida.procop.BR
import com.dida.procop.R
import com.dida.procop.databinding.FragmentSearchProductBinding
import dagger.hilt.android.AndroidEntryPoint
import me.gilo.woodroid.models.Product
import javax.inject.Inject

@AndroidEntryPoint
class SearchProductFragment :
    BaseNavViewModelFragment<FragmentSearchProductBinding, ISearchProduct.State, SearchProductVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: SearchProductVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_search_product
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = getString(R.string.search)
    override fun onClick(id: Int) {}
    override fun hasOptionMenu(): Boolean = true

    @Inject
    lateinit var searchProductAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewDataBinding.recyclerView.adapter = searchProductAdapter

        mViewDataBinding.searchBar.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                val searchText = mViewDataBinding.searchBar.text.toString()
                viewModel.searchProduct(searchText)
                true
            } else {
                false
            }
        }

        viewModel.searchProducts.observe(viewLifecycleOwner) {
            searchProductAdapter.setList(it)
        }

        searchProductAdapter.onItemClickListener = productClickListener
        searchProductAdapter.onChildItemClickListener = addToCartClickListener
    }

    private fun showKeyboard(activity: Activity, editText: EditText) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        editText.requestFocus()
        inputMethodManager.showSoftInput(editText, 0)
    }

    private val addToCartClickListener = { view: View, position: Int, data: Product ->
        when (view.id) {
            R.id.addToCartBtn -> {
                val messageId = viewModel.databaseHelper.addToCart(data)
                showToast(getString(messageId))
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
        mViewDataBinding.searchBar.requestFocus()
        mViewDataBinding.searchBar.postDelayed({ showKeyboard(requireActivity(), mViewDataBinding.searchBar) }, 1000)
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