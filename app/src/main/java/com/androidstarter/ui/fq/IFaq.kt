package com.androidstarter.ui.fq

import com.androidstarter.base.interfaces.IBase

interface IFaq {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}