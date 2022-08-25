package com.androidstarter.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.androidstarter.base.activity.BaseActivity
import com.androidstarter.base.extensions.hideKeyboard
import com.androidstarter.base.interfaces.CanHandleOnClick
import com.androidstarter.base.interfaces.IBase
import com.androidstarter.base.interfaces.OnClickHandler
import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.viewmodel.HiltBaseViewModel

abstract class BaseBindingViewModelFragment<VB : ViewDataBinding, VS : IBase.State, VM : HiltBaseViewModel<VS>> :
    BaseViewModelFragment<VS, VM>(), CanHandleOnClick {

    lateinit var mViewDataBinding: VB
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setupBindingView(inflater, container, layoutResId) {

//            performDataBinding(savedInstanceState)
            //it.setVariable(brViewVariableId, this)
//            it.setVariable(bindingVariableId, viewModel)
            //  it.setVariable(brViewStateVariableId, viewModel.value.viewState)
//            it.lifecycleOwner = this.viewLifecycleOwner
            //  _binding = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wasViewCreated = isViewCreated
        isViewCreated = true

        // performing the initialization only in cases when the view was created for the first time
        if (!wasViewCreated) {
            init(savedInstanceState)
            postInit()
        }
        performDataBinding(savedInstanceState)

    }

    override fun performDataBinding(savedInstanceState: Bundle?) {
        registerStateListeners()
        viewModel.fetchExtras(arguments)
        //viewModel.c = requireContext()
        if (bindingViewStateVariableId <= 0)
            throw IllegalArgumentException("The state  binding variable should not null or zero. Check your fragment ${javaClass.simpleName}. Fragment should override bindingViewStateVariableId and provide valid binding variable for state ")
        mViewDataBinding.setVariable(bindingViewStateVariableId, viewModel.viewState)
        if (bindingVariableId <= 0)
            throw IllegalArgumentException("The state  binding variable should not null or zero. Check your fragment ${javaClass.simpleName}. Fragment should override bindingVariableId and provide valid binding variable for viewModel")
        mViewDataBinding.setVariable(bindingVariableId, viewModel)
        mViewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        mViewDataBinding.executePendingBindings()
        if (viewModel is
                    IValidator
        ) {
            (viewModel as IValidator).validator?.targetViewBinding = mViewDataBinding
        }
        if (viewModel is OnClickHandler) {
            viewModel.clickEvent?.observe(viewLifecycleOwner, { onClick(it) })
        }
        postExecutePendingBindings(savedInstanceState)
        viewModel.onCreate(arguments)
    }

    private fun setupBindingView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        layoutResId: Int,
        set: (VB) -> Unit
    ): View {
        mViewDataBinding =
            DataBindingUtil.inflate<VB>(layoutInflater, layoutResId, container, false).also {
                set(it)
            }
        return mViewDataBinding.root
    }

    fun setupToolbar(
        toolbar: Toolbar?,
        toolbarMenu: Int? = null, setActionBar: Boolean = true,
        navigationOnClickListener: ((View) -> Unit?)? = null
    ) {
        toolbar?.apply {
            title = ""
            setHomeAsUpIndicator()?.let { setNavigationIcon(it) }
            (activity as BaseActivity).setSupportActionBar(this)
            navigationOnClickListener?.let { l -> this.setNavigationOnClickListener { l.invoke(it) } }
            if (setActionBar) {
                (activity as BaseActivity).supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled() ?: true)
                    setHomeButtonEnabled(setDisplayHomeAsUpEnabled() ?: true)
                    setDisplayShowCustomEnabled(setDisplayHomeAsUpEnabled() ?: true)
                    setHomeAsUpIndicator()?.let { setHomeAsUpIndicator(it) }
                }
            }
            toolbarMenu?.let { this.inflateMenu(it) }
        }
    }

    private fun registerStateListeners() {
        viewModel.registerLifecycleOwner(this)
    }

    private fun unregisterStateListeners() {
        viewModel.unregisterLifecycleOwner(this)
    }

    override fun onDestroyView() {
        unregisterStateListeners()
        hideKeyboard()
        super.onDestroyView()
    }

    override fun getFragmentResult(): Pair<String?, Bundle?>? = null
}
