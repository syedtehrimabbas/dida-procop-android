package com.androidstarter.ui.fvrt

import com.androidstarter.base.interfaces.IBase

interface IFavourite {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}