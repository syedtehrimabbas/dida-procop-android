package com.androidstarter.ui.settings

import com.androidstarter.base.interfaces.IBase

interface ISettings {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}