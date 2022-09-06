package com.androidstarter.ui.about

import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment :
    BaseNavViewModelFragment<FragmentAboutBinding, IAbout.State, AboutVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: AboutVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_about
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = "About"
    override fun onClick(id: Int) {}
}