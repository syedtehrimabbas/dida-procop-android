package com.androidstarter.ui.signup

import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment :
    BaseNavViewModelFragment<FragmentSignupBinding, ISignup.State, SignupVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: SignupVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_signup
    override fun toolBarVisibility(): Boolean = true

    override fun onClick(id: Int) {
        when (id) {
            R.id.lostPassword,200 -> navigateBack()
        }
    }
}