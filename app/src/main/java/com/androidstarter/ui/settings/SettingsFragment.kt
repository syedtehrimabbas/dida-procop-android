package com.androidstarter.ui.settings

import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseNavViewModelFragment<FragmentSettingsBinding, ISettings.State, SettingsVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: SettingsVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_settings
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = "Settings"
    override fun onClick(id: Int) {}
}