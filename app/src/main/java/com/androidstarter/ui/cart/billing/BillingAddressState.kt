package com.androidstarter.ui.cart.billing

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class BillingAddressState @Inject constructor() : BaseState(), IBillingAddress.State {
    override val firstName: MutableLiveData<String> = MutableLiveData("")
    override val lastName: MutableLiveData<String> = MutableLiveData("")
    override val email: MutableLiveData<String> = MutableLiveData("")
    override val country: MutableLiveData<String> = MutableLiveData()
    override val streetAddress: MutableLiveData<String> = MutableLiveData("")
    override val streetAddressOptional: MutableLiveData<String> = MutableLiveData()
    override val zipCode: MutableLiveData<String> = MutableLiveData("")
    override val city: MutableLiveData<String> = MutableLiveData()
    override val phoneNo: MutableLiveData<String> = MutableLiveData("")
    override val companyNameOptional: MutableLiveData<String> = MutableLiveData()
    override val showCompanyFields: MutableLiveData<Boolean> = MutableLiveData(false)
}