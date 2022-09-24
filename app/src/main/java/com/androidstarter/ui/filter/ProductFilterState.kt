package com.androidstarter.ui.filter

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class ProductFilterState @Inject constructor() : BaseState(), IProductFilter.State {
    override val filterApplied: MutableLiveData<Boolean> = MutableLiveData(false)
}