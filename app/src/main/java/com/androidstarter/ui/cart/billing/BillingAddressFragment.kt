package com.androidstarter.ui.cart.billing

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import com.dida.procop.BR
import com.dida.procop.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.dida.procop.databinding.FragmentBillingAddressBinding
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
    override fun getToolBarTitle() = getString(R.string.billing_address)
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
                    arguments?.putParcelable("billingAddress", billingAddress)
                    navigate(R.id.action_billingAddressFragment_to_cardsFragment, arguments)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            viewModel.validator?.validate()
            viewState.showCompanyFields.value = checkedId == R.id.company
        }
    }
}