package com.androidstarter.ui.starter

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.androidstarter.R
import com.androidstarter.base.KEY_IS_USER_LOGGED_IN
import com.androidstarter.base.extensions.launchActivity
import com.androidstarter.base.navgraph.host.NAVIGATION_Graph_ID
import com.androidstarter.base.navgraph.host.NAVIGATION_Graph_START_DESTINATION_ID
import com.androidstarter.base.navgraph.host.NavHostPresenterActivity
import com.androidstarter.data.sessions.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarterActivity : AppCompatActivity() {
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)
        sharedPreferenceManager = SharedPreferenceManager(applicationContext)

        Handler().postDelayed({
            if (sharedPreferenceManager.getValueBoolean(KEY_IS_USER_LOGGED_IN, false).not())
                navigateToLoginScreen()
            else
                navigateToDashboard()

        }, 2000)
//        navigateToDashboard()
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