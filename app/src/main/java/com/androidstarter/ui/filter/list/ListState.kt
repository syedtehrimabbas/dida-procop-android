package com.androidstarter.ui.filter.list

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class ListState @Inject constructor() : BaseState(), IList.State {
    override val listName: MutableLiveData<String> = MutableLiveData("")
}