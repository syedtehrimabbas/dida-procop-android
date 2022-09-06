package com.androidstarter.ui.fq

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentFaqBinding
import com.androidstarter.ui.home.adapter.FaqAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FaqFragment :
    BaseNavViewModelFragment<FragmentFaqBinding, IFaq.State, FaqVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: FaqVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_faq
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = "FAQ"
    override fun onClick(id: Int) {}

    @Inject
    lateinit var faqAdapter: FaqAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.faqDataList.observe(viewLifecycleOwner) {
            faqAdapter.setList(it)
        }
        mViewDataBinding.faqRV.adapter = faqAdapter
    }
}