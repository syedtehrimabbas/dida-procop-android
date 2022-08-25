package com.androidstarter.ui.starter

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.androidstarter.R
import com.androidstarter.base.extensions.launchActivity
import com.androidstarter.base.navgraph.host.NAVIGATION_Graph_ID
import com.androidstarter.base.navgraph.host.NAVIGATION_Graph_START_DESTINATION_ID
import com.androidstarter.base.navgraph.host.NavHostPresenterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)

        Handler().postDelayed({
            navigateToLoginScreen()
        }, 2000)
    }

    private fun navigateToLoginScreen() {
        launchActivity<NavHostPresenterActivity>(
            options = Bundle(),
            clearPrevious = true
        ) {
            putExtra(NAVIGATION_Graph_ID, R.navigation.onboarding_nav_graph)
            putExtra(
                NAVIGATION_Graph_START_DESTINATION_ID,
                R.id.loginFragment
            )
        }
    }
}