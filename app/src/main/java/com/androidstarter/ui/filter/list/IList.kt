package com.androidstarter.ui.filter.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase

interface IList {
    interface State : IBase.State {
        val listName: MutableLiveData<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val categoriesList: LiveData<ArrayList<String>>
    }
}