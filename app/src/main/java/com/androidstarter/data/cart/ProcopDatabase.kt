package com.androidstarter.data.cart

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidstarter.data.cart.converters.ListConverters
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.cart.models.CartMetaData
import com.androidstarter.data.cart.models.CartProduct
import com.androidstarter.data.cart.models.FavouriteProduct

@Database(
    entities = [CartProduct::class, CartMetaData::class, FavouriteProduct::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(ListConverters::class)
abstract class ProcopDatabase : RoomDatabase() {
    abstract fun getProductDao(): CartProductDao

    companion object {
        const val DB_NAME = "procop_cart.db"
    }
}