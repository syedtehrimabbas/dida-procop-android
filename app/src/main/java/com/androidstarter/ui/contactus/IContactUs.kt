package com.androidstarter.ui.contactus

import com.androidstarter.base.interfaces.IBase

interface IContactUs {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}