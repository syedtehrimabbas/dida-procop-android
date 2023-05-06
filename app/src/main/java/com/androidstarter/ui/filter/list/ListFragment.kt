package com.androidstarter.ui.filter.list

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.dida.procop.BR
import com.dida.procop.R
import com.dida.procop.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment :
    BaseNavViewModelFragment<FragmentListBinding, IList.State, ListVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: ListVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_list
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = getString(R.string.screen_product_filters_choose_category)
    override fun onClick(id: Int) {
    }

    @Inject
    lateinit var listAdapter: ListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.categoriesList.observe(viewLifecycleOwner) {
            listAdapter.setList(it)
        }
        mViewDataBinding.recyclerView.adapter = listAdapter
        listAdapter.onItemClickListener = { _: View, position: Int, data: String ->
            navigateBackWithResult(Activity.RESULT_OK, bundleOf(SELECTED_ITEM_DATA to SelectedListData(data, position)))
        }
    }

    @Parcelize
    data class SelectedListData(val name: String, val position: Int) : Parcelable
    companion object {
        const val SELECTED_ITEM_DATA = "SelectedItemData"
    }
}