package com.androidstarter.ui.cart.cards

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class CardsState @Inject constructor() : BaseState(), ICards.State {
    override val selected: MutableLiveData<Int> = MutableLiveData(1)
}