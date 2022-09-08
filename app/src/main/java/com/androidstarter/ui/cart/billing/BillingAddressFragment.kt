package com.androidstarter.ui.cart.billing

import androidx.fragment.app.viewModels
import com.androidstarter.BR
import com.androidstarter.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.androidstarter.databinding.FragmentBillingAddressBinding
import com.androidstarter.ui.cart.billing.data.BillingAddressData
import com.androidstarter.ui.cart.billing.data.CitiesBottomSheet
import com.androidstarter.ui.cart.billing.data.CountriesBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillingAddressFragment :
    BaseNavViewModelFragment<FragmentBillingAddressBinding, IBillingAddress.State, BillingAddressVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: BillingAddressVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_billing_address
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = "Billing Address"
    override fun onClick(id: Int) {
        when (id) {
            R.id.countryET -> {
                viewModel.countriesList.value?.let {
                    CountriesBottomSheet(it, viewModel).show(
                        parentFragmentManager,
                        "CountriesBottomSheet"
                    )
                }
            }
            R.id.cityNameET -> {
                viewModel.citiesList.value?.let {
                    CitiesBottomSheet(it, viewModel).show(
                        parentFragmentManager,
                        "CitiesBottomSheet"
                    )
                }
            }
            R.id.checkoutBT -> {
                with(viewState) {
                    val billingAddress = BillingAddressData(
                        streetAddress.value,
                        streetAddressOptional.value,
                        city.value,
                        country.value,
                        email.value,
                        firstName.value,
                        lastName.value,
                        phoneNo.value,
                        zipCode.value,
                        city.value,
                    )
                    navigate(R.id.cardsFragment)
                }
            }
        }
    }
}