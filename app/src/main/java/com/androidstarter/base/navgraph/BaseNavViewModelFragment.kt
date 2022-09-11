package com.androidstarter.base.navgraph

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.Slide
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.androidstarter.R
import com.androidstarter.base.BaseBindingViewModelFragment
import com.androidstarter.base.interfaces.IBase
import com.androidstarter.base.interfaces.ManageToolBarListener
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import kotlin.properties.Delegates

private const val ARGUMENT_NAVIGATION_REQUEST_CODE = "NAVIGATION_REQUEST_CODE"

const val DESTINATION_NOT_SET = -1
const val REQUEST_CODE_NOT_SET = -1

const val NAVIGATION_RESULT_CANCELED = 0
const val NAVIGATION_RESULT_OK = -1

abstract class BaseNavViewModelFragment<VB : ViewDataBinding, VS : IBase.State, VM : HiltBaseViewModel<VS>> :
    BaseBindingViewModelFragment<VB, VS, VM>() {
    protected open val hasUpNavigation: Boolean = true
    private val requestCode: Int
        get() = arguments?.getInt(ARGUMENT_NAVIGATION_REQUEST_CODE, REQUEST_CODE_NOT_SET)
            ?: REQUEST_CODE_NOT_SET

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        if (activity is ManageToolBarListener) {
            (activity as ManageToolBarListener).toolBarTitle = getToolBarTitle()
            (activity as ManageToolBarListener).toolBarVisibility = toolBarVisibility()
            (activity as ManageToolBarListener).displayHomeAsUpEnabled = setDisplayHomeAsUpEnabled()
            (activity as ManageToolBarListener).homeAsUpIndicator = setHomeAsUpIndicator()
        }
    }

    override fun getToolBarTitle(): String? = null
    override fun toolBarVisibility(): Boolean? = true
    override fun setDisplayHomeAsUpEnabled(): Boolean? = true
    override fun setHomeAsUpIndicator() = R.drawable.back_icon


    /**
     * Navigates to the specified destination screen.
     *
     * @param destinationId the id of the destination screen (either the new Activity or Fragment)
     * @param extras the extra arguments to be passed to the destination screen
     * @param navigationExtras
     */
    fun navigate(
        @IdRes destinationId: Int,
        extras: Bundle? = Bundle(),
        navigationExtras: Navigator.Extras? = null,
        navOptions: NavOptions? = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
    ) {
        getFragmentResult()?.apply {
            val (key, bundle) = this
            key?.let {
                setFragmentResult(it, bundle ?: bundleOf())
            }
        }

        findNavController().navigate(
            destinationId,
            extras,
            navOptions,
            navigationExtras,
        )
    }

    /**
     * Navigates to the specified destination screen.
     *
     * @param directions the direction that leads to the destiantion screen.
     * @param navigationExtras
     */
    protected fun navigate(directions: NavDirections, navigationExtras: Navigator.Extras? = null) {
        navigationExtras?.let { navExtras ->
            findNavController().navigate(directions, navExtras)
        } ?: run {
            findNavController().navigate(directions)
        }
    }

    fun navigateWithPopup(
        @IdRes destinationId: Int, @IdRes popupTo: Int, extras: Bundle? = Bundle(),
        navigationExtras: Navigator.Extras? = null, enableAnimation: Boolean = true
    ) {
        navigate(
            destinationId,
            extras,
            navigationExtras,
            navOptions = navOptions {
                popUpTo(popupTo) {
                    inclusive = true
                }
                if (enableAnimation) {
                    anim {
                        enter = R.anim.slide_in_right
                        exit = R.anim.slide_out_left
                        popEnter = R.anim.slide_in_left
                        popExit = R.anim.slide_out_right
                    }
                }
            })
    }

    /**
     * Navigates back (pops the back stack) to the previous [MvvmFragment] on the stack.
     */
    protected fun navigateBack() {
        findNavController().popBackStack()
    }

    protected fun navigateUp(): Boolean = findNavController().navigateUp()

    protected fun navigateForResultWithAnimation(
        requestCode: Int, navDirections: NavDirections, navOptions: NavOptions? = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
    ) {
        // val extras = FragmentNavigatorExtras(appBarLayout to appBarTransition)
        this.exitTransition = Fade()
        navigateForResult(
            resId = navDirections.actionId,
            requestCode = requestCode,
            args = navDirections.arguments,
            navOptions = navOptions,
            navigatorExtras = null
        )
    }

    protected fun navigateForResult(
        requestCode: Int, navDirections: NavDirections, navOptions: NavOptions? = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }, extras: Bundle? = Bundle(),

        navigatorExtras: Navigator.Extras? = null
    ) =
        navigateForResult(
            resId = navDirections.actionId,
            requestCode = requestCode,
            args = extras,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras,

            )

    protected fun navigateForResult(
        @IdRes resId: Int,
        requestCode: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        },
        navigatorExtras: Navigator.Extras? = null
    ) {
        val argsWithRequestCode = (args ?: Bundle()).apply {
            putInt(ARGUMENT_NAVIGATION_REQUEST_CODE, requestCode)
        }
        navigatorExtras?.let {
            findNavController().navigate(
                resId,
                argsWithRequestCode,
                navOptions,
                navigatorExtras
            )
        } ?: findNavController().navigate(resId, argsWithRequestCode, navOptions)
    }

    protected fun navigateForwardWithAnimation(navDirections: NavDirections) {
        // val extras = FragmentNavigatorExtras(appBarLayout to appBarTransition)
        this.exitTransition = Slide()
        findNavController().navigate(navDirections)
    }

    protected fun navigateForwardWithAnimation(
        navDirections: NavDirections,
        args: Bundle?,
        exitTransition: Any? = Slide()
    ) {
        // val extras = FragmentNavigatorExtras(appBarLayout to appBarTransition)
        // exitTransition?.let { this.exitTransition = it }
//        this.enterTransition = Slide(Gravity.RIGHT)
        navigateForResult(navDirections.actionId, REQUEST_CODE_NOT_SET, args)
    }

    protected fun navigate(
        navDirections: NavDirections,
        args: Bundle?
    ) {
        navigateForResult(navDirections.actionId, REQUEST_CODE_NOT_SET, args)
    }

    protected fun navigateBackWithResult(resultCode: Int, data: Bundle? = null): Boolean =
        navigateBackWithResult(
            DESTINATION_NOT_SET,
            BackNavigationResult(requestCode, resultCode, data)
        )

    protected fun navigateBackWithResult(
        @IdRes destination: Int,
        resultCode: Int,
        data: Bundle? = null
    ): Boolean =
        navigateBackWithResult(destination, BackNavigationResult(requestCode, resultCode, data))

    protected fun initEnterTransitions() {
        sharedElementEnterTransition = ChangeBounds()
        enterTransition = Fade()
    }

    private fun navigateBackWithResult(
        @IdRes destination: Int,
        result: BackNavigationResult
    ): Boolean {
        val childFragmentManager =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager
        var backStackListener: FragmentManager.OnBackStackChangedListener by Delegates.notNull()
        backStackListener = FragmentManager.OnBackStackChangedListener {
            (childFragmentManager?.fragments?.get(0) as? BackNavigationResultListener)?.onNavigationResult(
                result
            )
            childFragmentManager?.removeOnBackStackChangedListener(backStackListener)
        }
        childFragmentManager?.addOnBackStackChangedListener(backStackListener)
        val backStackPopped = if (destination == DESTINATION_NOT_SET) {
            findNavController().popBackStack()
        } else {
            findNavController().popBackStack(destination, true)
        }
        if (!backStackPopped) {
            childFragmentManager?.removeOnBackStackChangedListener(backStackListener)
        }
        return backStackPopped
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_cart_fvrt, menu)
    }

    fun navigateToCart() {
        navigate(R.id.cartFragment)
    }

    val underDevClick = View.OnClickListener {
        showToast("Under development")
    }
}