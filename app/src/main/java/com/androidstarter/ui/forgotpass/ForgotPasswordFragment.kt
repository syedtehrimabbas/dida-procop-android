package com.androidstarter.ui.forgotpass

import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseNavViewModelFragment<FragmentForgotPasswordBinding, IForgotPassword.State, ForgotPasswordVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: ForgotPasswordVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_forgot_password
    override fun onClick(id: Int) {
        navigate(R.id.action_forgotPasswordFragment_to_changePasswordFragment)
    }

    override fun toolBarVisibility(): Boolean = true
}