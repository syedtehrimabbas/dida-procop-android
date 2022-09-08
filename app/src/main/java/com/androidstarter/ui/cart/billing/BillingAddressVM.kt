package com.androidstarter.ui.cart.billing

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.cart.billing.data.CitySelector
import com.androidstarter.ui.cart.billing.data.CountrySelector
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import me.gilo.woodroid.models.countries.CountriesResponseItem
import me.gilo.woodroid.models.countries.CountryState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BillingAddressVM @Inject constructor(
    override val viewState: BillingAddressState,
    override var validator: Validator?,
    val woocommerce: Woocommerce
) : HiltBaseViewModel<IBillingAddress.State>(), IBillingAddress.ViewModel, IValidator,
    CountrySelector, CitySelector {
    var countriesList: MutableLiveData<List<CountriesResponseItem>> = MutableLiveData()
    var citiesList: MutableLiveData<List<CountryState>> = MutableLiveData()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        loading(true)
        woocommerce.ProductRepository().countries()
            .enqueue(object : Callback<ArrayList<CountriesResponseItem>> {
                override fun onResponse(
                    call: Call<java.util.ArrayList<CountriesResponseItem>>,
                    response: Response<java.util.ArrayList<CountriesResponseItem>>
                ) {
                    loading(false)
                    response.let {
                        if (it.isSuccessful) {
                            it.body()?.let { countries ->
                                countriesList.postValue(countries)
                                if (countries.size > 0) {
                                    onCountrySelect(countries[0])
                                }
                            }
                        }
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<CountriesResponseItem>>,
                    t: Throwable
                ) {
                    t.message?.let { loading(false, it) }
                }
            })
    }

    override fun onCountrySelect(country: CountriesResponseItem) {
        viewState.country.postValue(country.name)
        citiesList.postValue(country.states)
        if (country.states.isNotEmpty())
            onCitySelect(country.states[0])
    }

    override fun onCitySelect(city: CountryState) {
        viewState.city.postValue(city.name)
    }
}