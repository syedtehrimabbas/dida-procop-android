package com.androidstarter.ui.productdetails

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.clickevents.setOnClick
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.databinding.FragmentProductDetailsBinding
import com.androidstarter.ui.home.adapter.ProductAttributeAdapter
import com.androidstarter.ui.interfaces.ProductUpdateListener
import com.androidstarter.ui.productdetails.adapter.ProductImagesSliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
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
    override fun getToolBarTitle() = ""
    override fun toolBarVisibility(): Boolean = true
    override fun hasOptionMenu() = true
    override fun onClick(id: Int) {
        when (id) {
            R.id.addToCartBtn -> {
                viewModel.databaseHelper.cartCount()
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

            R.id.addToFavBtn -> {
                viewModel.databaseHelper.favouriteCount()
                if (viewModel.isFav) {
                    unFavourite()
                } else {
                    favourite()
                }
                val product = viewState.product.value
                product?.let {
                    viewModel.databaseHelper.addToFav(product)
                }
            }
        }
    }

    @Inject
    lateinit var attributeAdapter: ProductAttributeAdapter

    @Inject
    lateinit var productImagesSliderAdapter: ProductImagesSliderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewState.product.observe(viewLifecycleOwner) {
            viewModel.launch(Dispatcher.Background) {
                viewModel.databaseHelper.getProductById(it.id)?.let { _ ->
                    setRemoveCartText()
                } ?: setAddCartText()

                viewModel.databaseHelper.getFavProductById(it.id)?.let { _ ->
                    favourite()
                } ?: unFavourite()
            }
            attributeAdapter.setList(it.productAttributes.filter { productAttribute -> productAttribute.isVisible })

            productImagesSliderAdapter.setList(it.images)
        }
        initRecyclerView()
        setupSlider()
    }

    private fun setAddCartText() {
        viewModel.isInCart = false
        viewModel.launch(Dispatcher.Main) {
            mViewDataBinding.addToCartBtn.text = getString(R.string.common_add_to_cart)
        }
    }

    private fun setRemoveCartText() {
        viewModel.isInCart = true
        viewModel.launch(Dispatcher.Main) {
            mViewDataBinding.addToCartBtn.text = getString(R.string.common_remove_to_cart)
        }
    }

    private fun unFavourite() {
        viewModel.isFav = false
        viewModel.launch(Dispatcher.Main) {
            mViewDataBinding.addToFavBtn.setImageDrawable(context?.let {
                AppCompatResources.getDrawable(
                    it, R.drawable.circle_white_heart
                )
            })
        }
    }

    private fun favourite() {
        viewModel.isFav = true
        viewModel.launch(Dispatcher.Main) {
            mViewDataBinding.addToFavBtn.setImageDrawable(context?.let {
                AppCompatResources.getDrawable(
                    it, R.drawable.circle_red_heart
                )
            })
        }
    }

    private fun initRecyclerView() {
        with(mViewDataBinding) {
            attributeAdapter.onItemClickListener = attributeClickListener
            attributeRecyclerView.adapter = attributeAdapter
        }
    }

    private fun setupSlider() {
        with(mViewDataBinding) {
            sliderView.setSliderAdapter(productImagesSliderAdapter)
            sliderView.setIndicatorAnimation(
                IndicatorAnimationType.WORM
            ) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
            sliderView.scrollTimeInSec = 4 //set scroll delay in seconds :
            sliderView.startAutoCycle()
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

                    viewState.product.value?.productAttributes?.filter { productAttribute -> productAttribute.isVisible }?.let { productAttributes ->
                        attributeAdapter.setList(productAttributes)
                    }
                    viewModel.setVariationPrice()
                }
            }
        }
    }

    private val attributeClickListener =
        { view: View, position: Int, data: ProductAttribute? ->
            AttributeBottomSheet(data, this).show(parentFragmentManager, "AttributeBottomSheet")
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