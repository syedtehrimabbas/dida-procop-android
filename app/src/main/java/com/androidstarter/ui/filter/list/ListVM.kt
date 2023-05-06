package com.androidstarter.ui.filter.list

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.gilo.woodroid.Woocommerce
import javax.inject.Inject

@HiltViewModel
class ListVM @Inject constructor(
    override val viewState: ListState,
    private val woocommerce: Woocommerce,
) : HiltBaseViewModel<IList.State>(), IList.ViewModel {
    private val _categoriesList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    override val categoriesList: LiveData<ArrayList<String>> = _categoriesList
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)
        val list: ArrayList<String> = bundle?.getStringArrayList("list") as ArrayList<String>
        _categoriesList.value = list
    }
}