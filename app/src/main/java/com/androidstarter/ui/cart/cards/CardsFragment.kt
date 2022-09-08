package com.androidstarter.ui.cart.cards

import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentCardsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment :
    BaseNavViewModelFragment<FragmentCardsBinding, ICards.State, CardsVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: CardsVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_cards
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = "Cards"
    override fun onClick(id: Int) {
        when (id) {
            R.id.cardPayment -> viewState.selected.value = 1
            R.id.paypalPayment -> viewState.selected.value = 2
        }
    }
}