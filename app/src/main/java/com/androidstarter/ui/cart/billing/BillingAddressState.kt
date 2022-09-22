package com.androidstarter.ui.cart.billing

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.state.BaseState
import javax.inject.Inject

class BillingAddressState @Inject constructor() : BaseState(), IBillingAddress.State {
    override val firstName: MutableLiveData<String> = MutableLiveData("Tehrim")
    override val lastName: MutableLiveData<String> = MutableLiveData("Abbas")
    override val email: MutableLiveData<String> = MutableLiveData("tehrim@gmail.com")
    override val country: MutableLiveData<String> = MutableLiveData()
    override val streetAddress: MutableLiveData<String> = MutableLiveData("Thokar Niaz beg Ali Town near Gourmet Bakery")
    override val streetAddressOptional: MutableLiveData<String> = MutableLiveData()
    override val zipCode: MutableLiveData<String> = MutableLiveData("53700")
    override val city: MutableLiveData<String> = MutableLiveData()
    override val phoneNo: MutableLiveData<String> = MutableLiveData("03405104524")
    override val companyNameOptional: MutableLiveData<String> = MutableLiveData()
}