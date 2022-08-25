package com.androidstarter.base.activity

import android.os.Bundle
import com.androidstarter.base.interfaces.CanFetchExtras
import com.androidstarter.base.interfaces.IBase

abstract class BaseViewModelActivity<VS : IBase.State, VM : IBase.ViewModel<VS>> :
    BaseActivity(), IBase.View<VM>, CanFetchExtras {

    /**
     * Indicates whether the current [BaseBindingFragment]'s content view is initialized or not.
     */
    var isViewCreated = false
        private set

    /**
     * Reference to Fragment ViewState
     */
    val viewState: VS
        get() {
            return viewModel.viewState
        }

    //    override val viewModel: Lazy<VM> by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // the overall initialization, extras fetching and post initialization will be performed only once, too
        if (!isViewCreated) {
            preInit(savedInstanceState)
        }
        viewModel.registerLifecycleOwner(this)
    }

    override fun getScreenName() = this::class.java.simpleName
}