package com.androidstarter.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.dida.procop.BR
import com.dida.procop.R
import com.androidstarter.base.extensions.launchActivity
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.base.navgraph.host.NAVIGATION_Graph_ID
import com.androidstarter.base.navgraph.host.NAVIGATION_Graph_START_DESTINATION_ID
import com.androidstarter.base.navgraph.host.NavHostPresenterActivity
import com.dida.procop.databinding.FragmentLoginBinding
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
            R.id.lostPassword -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://papiersprocop.com/mon-compte/lost-password/")))
            }
            200 -> navigateToDashboard()
        }
    }

    private fun navigateToDashboard() {
        launchActivity<NavHostPresenterActivity>(
            options = Bundle(),
            clearPrevious = true
        ) {
            putExtra(NAVIGATION_Graph_ID, R.navigation.home_nav_graph)
            putExtra(
                NAVIGATION_Graph_START_DESTINATION_ID,
                R.id.homeFragment
            )
        }
    }
}