package com.androidstarter.ui.search

import androidx.lifecycle.LiveData
import com.androidstarter.base.interfaces.IBase
import me.gilo.woodroid.models.Product

interface ISearchProduct {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val searchProducts: LiveData<List<Product>>
    }
}