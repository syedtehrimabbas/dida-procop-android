package com.androidstarter.ui.profile

import com.androidstarter.base.interfaces.IBase

interface IProfile {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}