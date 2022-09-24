package com.androidstarter.ui.filter

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
import com.androidstarter.databinding.FragmentContactUsBinding
import com.androidstarter.databinding.FragmentProductFilterBinding
import com.androidstarter.ui.home.adapter.BrandsAdapter
import com.androidstarter.ui.home.adapter.FilterColorsAdapter
import com.androidstarter.ui.home.adapter.ProductCategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import me.gilo.woodroid.models.AttributeTerm
import me.gilo.woodroid.models.Category
import javax.inject.Inject

@AndroidEntryPoint
class ProductFilterFragment :
    BaseNavViewModelFragment<FragmentProductFilterBinding, IProductFilter.State, ProductFilterVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: ProductFilterVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_product_filter
    override fun toolBarVisibility(): Boolean = false
    override fun getToolBarTitle() = ""
    override fun onClick(id: Int) {
        when (id) {
            R.id.closeBtn -> navigateBack()
            R.id.filterButton -> {
                arguments?.putSerializable("filters", viewModel.filters)
                navigate(R.id.action_productFilterFragment_to_filteredProductListFragment,arguments)
            }
        }
    }

    var adapter: ProductCategoriesAdapter = ProductCategoriesAdapter()

    @Inject
    lateinit var brandsAdapter: BrandsAdapter

    @Inject
    lateinit var thicknessAdapter: BrandsAdapter

    @Inject
    lateinit var colorsAdapter: FilterColorsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.catRecyclerView.adapter = adapter
        mViewDataBinding.colorsRecyclerView.adapter = colorsAdapter
        mViewDataBinding.brandsRecyclerView.adapter = brandsAdapter
        mViewDataBinding.thicknessRecyclerView.adapter = thicknessAdapter

        viewModel.categoriesList.observe(viewLifecycleOwner, ::setCategories)
        viewModel.colors.observe(viewLifecycleOwner, ::setColorsList)
        viewModel.brands.observe(viewLifecycleOwner, ::setBrandsList)
        viewModel.thickNess.observe(viewLifecycleOwner, ::setThicknessList)

        adapter.onItemClickListener = categoryClickListener
        colorsAdapter.onItemClickListener = colorsClickListener
        brandsAdapter.onItemClickListener = brandsClickListener
        thicknessAdapter.onItemClickListener = thicknessClickListener
    }

    private val categoryClickListener = { view: View, position: Int, data: Category? ->
        viewState.filterApplied.value = true
        viewModel.updateFilter("category", data?.id.toString())
    }

    private val brandsClickListener = { view: View, position: Int, data: AttributeTerm? ->
        viewState.filterApplied.value = true
        viewModel.updateFilter("pa_marque", data?.slug)
    }
    private val colorsClickListener =
        { view: View, position: Int, data: ProductFilterVM.FilterColor? ->
            viewState.filterApplied.value = true
            viewModel.updateFilter("pa_couleur", data?.slug)
        }

    private val thicknessClickListener = { view: View, position: Int, data: AttributeTerm? ->
        viewState.filterApplied.value = true
        viewModel.updateFilter("pa_epaisseur", data?.slug)
    }

    private fun setCategories(list: List<Category>) = adapter.setList(list)
    private fun setColorsList(list: List<ProductFilterVM.FilterColor>) = colorsAdapter.setList(list)
    private fun setBrandsList(list: List<AttributeTerm>) = brandsAdapter.setList(list)
    private fun setThicknessList(list: List<AttributeTerm>) = thicknessAdapter.setList(list)
}