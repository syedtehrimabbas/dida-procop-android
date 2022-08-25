package com.androidstarter.ui.login

import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :
    BaseNavViewModelFragment<FragmentLoginBinding, ILogin.State, LoginVM>() {

    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: LoginVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_login
    override fun toolBarVisibility(): Boolean = false
    override fun onClick(id: Int) {
        when (id) {
            R.id.registerScreenBtn -> navigate(R.id.signupFragment)
        }
    }
}