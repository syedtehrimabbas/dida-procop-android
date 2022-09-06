package com.androidstarter.data.cart.converters

import androidx.room.TypeConverter
import com.androidstarter.data.cart.models.CartMetaData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverters {
    @TypeConverter
    fun fromListToString(list: ArrayList<CartMetaData>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(metaDataString: String): ArrayList<CartMetaData> {
        val listType = object : TypeToken<ArrayList<CartMetaData>>() {}.type
        return Gson().fromJson(metaDataString, listType)
    }
}