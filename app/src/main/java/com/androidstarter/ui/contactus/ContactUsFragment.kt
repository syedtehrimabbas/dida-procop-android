package com.androidstarter.ui.contactus

import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentContactUsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment :
    BaseNavViewModelFragment<FragmentContactUsBinding, IContactUs.State, ContactUsVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: ContactUsVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_contact_us
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = "Contact"
    override fun onClick(id: Int) {}
}