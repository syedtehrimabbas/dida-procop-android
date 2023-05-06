package com.androidstarter.ui.filter

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.androidstarter.base.extensions.toast
import com.androidstarter.base.navgraph.BackNavigationResult
import com.androidstarter.base.navgraph.BackNavigationResultListener
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.ui.filter.list.ListFragment
import com.androidstarter.ui.filter.list.ListFragment.Companion.SELECTED_ITEM_DATA
import com.androidstarter.ui.home.adapter.BrandsAdapter
import com.androidstarter.ui.home.adapter.FilterColorsAdapter
import com.dida.procop.BR
import com.dida.procop.R
import com.dida.procop.databinding.FragmentProductFilterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import me.gilo.woodroid.models.AttributeTerm
import javax.inject.Inject

@AndroidEntryPoint
class ProductFilterFragment :
    BaseNavViewModelFragment<FragmentProductFilterBinding, IProductFilter.State, ProductFilterVM>(), BackNavigationResultListener {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: ProductFilterVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_product_filter
    override fun toolBarVisibility(): Boolean = false
    override fun getToolBarTitle() = ""
    override fun onClick(id: Int) {
        when (id) {
            R.id.closeBtn -> navigateBack()
            R.id.catSpinner -> {
                val list = viewModel.categoriesList.value
                if (list?.isNotEmpty() == true) {
                    navigateForResult(
                        requestCode = CATEGORY_NAV_CODE,
                        navDirections = ProductFilterFragmentDirections.actionProductFilterFragmentToListFragment(),
                        extras = bundleOf("list" to list.map { it.name })
                    )
                } else {
                    toast("No Category")
                }
            }
            R.id.subCatSpinner -> {
                if (viewState.selectedCategory.value.isNullOrEmpty()) {
                    val builder = MaterialAlertDialogBuilder(requireContext())
                    builder.setTitle(getString(R.string.error_title))
                    builder.setMessage(getString(R.string.choose_a_category))
                    builder.setCancelable(true)
                    builder.setPositiveButton("Ok") { dialog, which ->
                    }
                    builder.show()
                } else {
                    val list = viewModel.subCategoriesList.value
                    navigateForResult(
                        requestCode = SUB_CATEGORY_NAV_CODE,
                        navDirections = ProductFilterFragmentDirections.actionProductFilterFragmentToListFragment(),
                        extras = bundleOf("list" to list?.map { it.name })
                    )
                }
            }
            R.id.filterButton -> {
                arguments?.putSerializable("filters", viewModel.filters)
                navigate(R.id.action_productFilterFragment_to_filteredProductListFragment, arguments)
            }
        }
    }

    @Inject
    lateinit var brandsAdapter: BrandsAdapter

    @Inject
    lateinit var thicknessAdapter: BrandsAdapter

    @Inject
    lateinit var colorsAdapter: FilterColorsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.colorsRecyclerView.adapter = colorsAdapter
        mViewDataBinding.brandsRecyclerView.adapter = brandsAdapter
        mViewDataBinding.thicknessRecyclerView.adapter = thicknessAdapter
        viewModel.colors.observe(viewLifecycleOwner, ::setColorsList)
        viewModel.brands.observe(viewLifecycleOwner, ::setBrandsList)
        viewModel.thickNess.observe(viewLifecycleOwner, ::setThicknessList)

        colorsAdapter.onItemClickListener = colorsClickListener
        brandsAdapter.onItemClickListener = brandsClickListener
        thicknessAdapter.onItemClickListener = thicknessClickListener
        viewState.search.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                viewModel.removeFilter("search")
            } else {
                viewState.filterApplied.value = true
                viewModel.updateFilter("search", it)
            }
        }
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

    private fun setColorsList(list: List<ProductFilterVM.FilterColor>) = colorsAdapter.setList(list)
    private fun setBrandsList(list: List<AttributeTerm>) = brandsAdapter.setList(list)
    private fun setThicknessList(list: List<AttributeTerm>) = thicknessAdapter.setList(list)
    override fun onNavigationResult(result: BackNavigationResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            when (result.requestCode) {
                CATEGORY_NAV_CODE -> {
                    val data = result.data?.getParcelable<ListFragment.SelectedListData>(SELECTED_ITEM_DATA)
                    mViewDataBinding.catSpinner.text = data?.name
                    viewModel.categoriesList.value?.let { categoriesList ->
                        if (data !== null) {
                            val selectedCategory = categoriesList[data.position]
                            viewState.selectedCategory.value = selectedCategory.name
                            viewState.filterApplied.value = true
                            viewModel.fetchSubCategories(selectedCategory.id)
                            viewModel.updateFilter("category", selectedCategory.id.toString())
                        }
                    }
                }
                SUB_CATEGORY_NAV_CODE -> {
                    val data = result.data?.getParcelable<ListFragment.SelectedListData>(SELECTED_ITEM_DATA)
                    mViewDataBinding.subCatSpinner.text = data?.name
                    viewModel.subCategoriesList.value?.let { list ->
                        if (data !== null) {
                            val category = list[data.position]
                            viewState.filterApplied.value = true
                            viewModel.fetchSubCategories(category.id)
                            viewModel.updateFilter("sub_category", category.id.toString())
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val CATEGORY_NAV_CODE = 1111
        const val SUB_CATEGORY_NAV_CODE = 2222
    }
}