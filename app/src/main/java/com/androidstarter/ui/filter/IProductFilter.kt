package com.androidstarter.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase
import me.gilo.woodroid.models.Category

interface IProductFilter {
    interface State : IBase.State {
        val filterApplied: MutableLiveData<Boolean>
        val selectedCategory: MutableLiveData<String>
        val selectedSubCategory: MutableLiveData<String>
        val search :MutableLiveData<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val categoriesList: LiveData<ArrayList<Category>>
        val subCategoriesList: LiveData<ArrayList<Category>>
    }
}