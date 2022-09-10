package com.androidstarter.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase
import me.gilo.woodroid.models.Category
import me.gilo.woodroid.models.Product

interface IHome {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val categoriesList: LiveData<MutableList<Category>>
        val currentOfferProducts: LiveData<List<Product>>
        val procopExclusiveProducts: LiveData<List<Product>>
        val marquesEnTendanceProducts: LiveData<List<Product>>
        fun fetchCategories()
        fun fetchOnSaleProducts()
        fun fetchProcopExclusive()
        fun fetchMarquesEnTendance()
    }
}