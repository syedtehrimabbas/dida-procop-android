package com.androidstarter.ui.home

import androidx.lifecycle.LiveData
import com.androidstarter.base.interfaces.IBase
import me.gilo.woodroid.models.Category
import me.gilo.woodroid.models.Product

interface IHome {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val categoriesList: LiveData<ArrayList<Category>>
        val colorProducts: LiveData<List<Product>>
        val gmundusedProducts: LiveData<List<Product>>
        val offsetProducts: LiveData<List<Product>>
        val numericProducts: LiveData<List<Product>>
        val nuancierProducts: LiveData<List<Product>>
        val offresProducts: LiveData<List<Product>>
        fun fetchCategories()
        fun fetchProductByCategories(catId:Int)
//        fun fetchProcopExclusive()
//        fun fetchMarquesEnTendance()
    }
}