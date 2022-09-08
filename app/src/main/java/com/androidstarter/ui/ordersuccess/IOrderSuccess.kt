package com.androidstarter.ui.ordersuccess

import com.androidstarter.base.interfaces.IBase

interface IOrderSuccess {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}