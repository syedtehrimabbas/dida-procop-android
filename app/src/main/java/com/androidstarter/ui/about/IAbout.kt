package com.androidstarter.ui.about

import com.androidstarter.base.interfaces.IBase

interface IAbout {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}