package com.androidstarter.base.navgraph.host

import com.androidstarter.base.interfaces.IBase

interface INavHostPresenter {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}