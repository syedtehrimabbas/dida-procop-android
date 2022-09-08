package com.androidstarter.ui.cart.cards

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase

interface ICards {
    interface State : IBase.State {
        val selected: MutableLiveData<Int>
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}