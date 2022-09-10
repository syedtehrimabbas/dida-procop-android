package com.androidstarter.ui.productslist

import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsListVM @Inject constructor(
    override val viewState: ProductListState,
    val databaseHelper: DatabaseHelper
) : HiltBaseViewModel<IProductList.State>(), IProductList.ViewModel {

}