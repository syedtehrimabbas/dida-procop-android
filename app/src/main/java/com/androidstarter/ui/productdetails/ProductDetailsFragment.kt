package com.androidstarter.ui.productdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.databinding.FragmentProductDetailsBinding
import com.androidstarter.ui.home.adapter.ProductAttributeAdapter
import com.androidstarter.ui.interfaces.ProductUpdateListener
import dagger.hilt.android.AndroidEntryPoint
import me.gilo.woodroid.models.Product
import me.gilo.woodroid.models.ProductAttribute
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailsFragment :
    BaseNavViewModelFragment<FragmentProductDetailsBinding, IProductDetails.State, ProductDetailsVM>(),
    ProductUpdateListener {

    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: ProductDetailsVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_product_details
    override fun toolBarVisibility(): Boolean = true
    override fun onClick(id: Int) {
        when (id) {
            R.id.addToCartBtn -> {
                if (viewModel.isInCart) {
                    setAddCartText()
                } else {
                    setRemoveCartText()
                }
                val product = viewState.product.value
                product?.let {
                    viewModel.databaseHelper.addToCart(product)
                }
            }
        }
    }

    @Inject
    lateinit var attributeAdapter: ProductAttributeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewState.product.observe(viewLifecycleOwner) {
            viewModel.launch(Dispatcher.Background) {
                viewModel.cartProductDao.getProductById(it.id)?.let { _ ->
                    setRemoveCartText()
                } ?: setAddCartText()
            }
            attributeAdapter.setList(it.productAttributes)
        }
        initRecyclerView()
    }

    private fun setAddCartText() {
        viewModel.isInCart = false
        viewModel.launch(Dispatcher.Main) {
            mViewDataBinding.addToCartBtn.text = "Add to cart"
        }
    }

    private fun setRemoveCartText() {
        viewModel.isInCart = true
        viewModel.launch(Dispatcher.Main) {
            mViewDataBinding.addToCartBtn.text = "Remove Cart"
        }
    }

    private fun initRecyclerView() {
        with(mViewDataBinding) {
            attributeAdapter.onItemClickListener = attributeClickListener
            attributeRecyclerView.adapter = attributeAdapter
        }
    }

    override fun onProductUpdate(product: Product) {

    }

    override fun onAttributeUpdate(attribute: ProductAttribute, selectedValue: String) {
        val productAttribute =
            viewState.product.value?.productAttributes?.find { it.id == attribute.id }
        val index = viewState.product.value?.productAttributes?.indexOf(productAttribute)
        if (index != null) {
            if (index > -1) {
                productAttribute?.selectedAttribute = selectedValue
                productAttribute?.let {
                    viewState.product.value?.productAttributes?.set(index, it)
                    viewState.product.value?.productAttributes?.let { it1 ->
                        attributeAdapter.setList(
                            it1
                        )
                    }
                }
            }
        }
    }

    private val attributeClickListener =
        { view: View, position: Int, data: ProductAttribute? ->
            AttributeBottomSheet(data, this).show(parentFragmentManager, "AttributeBottomSheet")
        }
}