package com.androidstarter.ui.orders

import com.androidstarter.base.interfaces.IBase

interface IOrders {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}