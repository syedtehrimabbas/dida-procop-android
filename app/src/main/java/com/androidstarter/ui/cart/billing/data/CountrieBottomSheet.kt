package com.androidstarter.ui.cart.billing.data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dida.procop.R
import com.dida.procop.databinding.FragmentBottomSheetAttributeBinding
import com.androidstarter.ui.home.adapter.CountriesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.gilo.woodroid.models.countries.CountriesResponseItem

class CountriesBottomSheet constructor(
    private val citiesList: List<CountriesResponseItem>,
    val countrySelector: CountrySelector
) :
    BottomSheetDialogFragment() {

    lateinit var listAdapter: CountriesAdapter

    private lateinit var mViewDataBinding: FragmentBottomSheetAttributeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        mViewDataBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_bottom_sheet_attribute,
                container,
                false
            )

        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = CountriesAdapter()
        listAdapter.onItemClickListener = clickListener
        mViewDataBinding.attributeTitle.text = "Country/Region"
        mViewDataBinding.listRecyclerView.adapter = listAdapter
        listAdapter.setList(citiesList)
    }

    private val clickListener =
        { view: View, position: Int, data: CountriesResponseItem ->
            countrySelector.onCountrySelect(data)
            dismiss()
        }
}

interface CountrySelector {
    fun onCountrySelect(country: CountriesResponseItem)
}