package com.androidstarter.ui.cart.billing.data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.androidstarter.R
import com.androidstarter.databinding.FragmentBottomSheetAttributeBinding
import com.androidstarter.ui.home.adapter.CitiesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.gilo.woodroid.models.countries.CountryState

class CitiesBottomSheet constructor(
    private val citiesList: List<CountryState>,
    val citySelector: CitySelector
) :
    BottomSheetDialogFragment() {

    lateinit var listAdapter: CitiesAdapter

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
        listAdapter = CitiesAdapter()
        listAdapter.onItemClickListener = clickListener
        mViewDataBinding.attributeTitle.text = "City"
        mViewDataBinding.listRecyclerView.adapter = listAdapter
        listAdapter.setList(citiesList)
    }

    private val clickListener =
        { view: View, position: Int, data: CountryState ->
            citySelector.onCitySelect(data)
            dismiss()
        }
}

interface CitySelector {
    fun onCitySelect(city: CountryState)
}