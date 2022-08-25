package com.androidstarter.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.androidstarter.base.extensions.toast
import com.androidstarter.base.interfaces.CanFetchExtras
import com.androidstarter.base.interfaces.IBase
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.base.state.UIState

abstract class BaseViewModelFragment<VS : IBase.State, VM : IBase.ViewModel<VS>> :
    BaseFragment(), IBase.View<VM>, CanFetchExtras {
    private var progress: ProgressDialogueFragment? = null

    /**
     * Indicates whether the current [BaseBindingViewModelFragment]'s content view is initialized or not.
     */
    var isViewCreated = false
        protected set

    /**
     * Reference to Fragment ViewState
     */
    val viewState: VS
        get() {
            return viewModel.viewState
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // the overall initialization, extras fetching and post initialization will be performed only once, too
        if (!isViewCreated) {
            arguments?.let(::fetchExtras)
            preInit()
        }
//        viewModel.value.registerLifecycleOwner(this)
        if (progress == null)
            progress = createProgressDialog()
        setHasOptionsMenu(hasOptionMenu())

        viewState.uiState.observe(this) {

            when (it) {
                is UIState.Loading -> {
                    showLoader(it.isLoading)
                    if (it.message.isNotEmpty()) {
                        showToast(it.message)
                    }
                }
                is UIState.Alert -> {
                    showToast(it.message)
                }
                is UIState.Error -> showToast(it.message)
                is UIState.Message -> showToast(it.message)
            }
        }
    }

    /**
     * Get reference to Activity ViewModel. Make sure correct VM class is
     * specified.
     */
    inline fun <reified AVM : HiltBaseViewModel<*>> getActivityViewModel(): AVM =
        ViewModelProvider(requireActivity()).get(AVM::class.java)

    override fun fetchExtras(extras: Bundle?) {

    }

    fun showLoader(isVisible: Boolean) {
        if (isVisible) {
            if (isResumed && userVisibleHint) {
                if (progress == null && progress?.isAdded == false) {
                    progress = createProgressDialog()
                    progress?.show(childFragmentManager, progress?.tag)
                } else {
                    progress?.show(childFragmentManager, progress?.tag)
                }
            }
        } else {
            if (progress?.isAdded == true)
                progress?.dismiss()
        }
    }

    private fun createProgressDialog(): ProgressDialogueFragment {
        return ProgressDialogueFragment()
    }

    override fun showToast(msg: String) {
        this.toast(msg)
    }

    override fun getScreenName() = this::class.java.simpleName
    override fun hasOptionMenu() = false
}