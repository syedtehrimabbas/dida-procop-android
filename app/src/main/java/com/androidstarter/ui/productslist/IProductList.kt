package com.androidstarter.ui.productslist

import com.androidstarter.base.interfaces.IBase

interface IProductList {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}