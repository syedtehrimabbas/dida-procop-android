package com.androidstarter.ui.fvrt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.cart.models.CartProduct
import com.androidstarter.data.cart.models.FavouriteProduct
import com.androidstarter.ui.home.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteVM @Inject constructor(
    override val viewState: FavouriteState,
    val databaseHelper: DatabaseHelper,
    val cartProductDao: CartProductDao
) : HiltBaseViewModel<IFavourite.State>(), IFavourite.ViewModel {

    private val _favouriteProducts: MutableLiveData<MutableList<FavouriteProduct>> = MutableLiveData()
    val favouriteProducts: LiveData<MutableList<FavouriteProduct>> = _favouriteProducts

    init {
        loadFavouriteItems()
    }

    private fun loadFavouriteItems() {
        launch(Dispatcher.LongOperation) {
            _favouriteProducts.postValue(cartProductDao.getFavProducts())
        }
    }

}