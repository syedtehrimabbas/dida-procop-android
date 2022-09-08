package com.androidstarter.ui.cart.billing

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.interfaces.IBase

interface IBillingAddress {
    interface State : IBase.State {
        val firstName: MutableLiveData<String>
        val lastName: MutableLiveData<String>
        val email: MutableLiveData<String>
        val country: MutableLiveData<String>
        val streetAddress: MutableLiveData<String>
        val streetAddressOptional: MutableLiveData<String>
        val zipCode: MutableLiveData<String>
        val city: MutableLiveData<String>
        val phoneNo: MutableLiveData<String>
        val companyNameOptional: MutableLiveData<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
    }
}