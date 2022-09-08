package com.androidstarter.data.cart

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidstarter.data.cart.converters.ListConverters
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.cart.models.CartMetaData
import com.androidstarter.data.cart.models.CartProduct

@Database(
    entities = [CartProduct::class, CartMetaData::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ListConverters::class)
abstract class ProcopDatabase : RoomDatabase() {
    abstract fun getProductDao(): CartProductDao

    companion object {
        const val DB_NAME = "procop_cart.db"
    }
}