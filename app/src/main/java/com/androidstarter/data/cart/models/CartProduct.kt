package com.androidstarter.data.cart.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = TableNames.ProductTable)
data class CartProduct(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    var productId: Int = 0,

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0,

    @ColumnInfo(name = "name")
    var productName: String = "",

    @ColumnInfo(name = "unit_price")
    var unitPrice: Double = 0.0,

    @ColumnInfo(name = "image")
    var productImage: String = "",

    @ColumnInfo(name = "meta_data")
    var metaDate: ArrayList<CartMetaData> = arrayListOf()
)

@Entity(tableName = TableNames.MetaDataTable)
@Parcelize
data class CartMetaData(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val displayKey: String = "",
    val displayValue: String = "",
    val key: String = "",
    val value: String = ""
) : Parcelable

@Entity(tableName = TableNames.FavouriteProductTable)
data class FavouriteProduct(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    var productId: Int = 0,

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0,

    @ColumnInfo(name = "name")
    var productName: String = "",

    @ColumnInfo(name = "unit_price")
    var unitPrice: Double = 0.0,

    @ColumnInfo(name = "image")
    var productImage: String = "",

    @ColumnInfo(name = "meta_data")
    var metaDate: ArrayList<CartMetaData> = arrayListOf()
)

object TableNames {
    const val ProductTable = "Product"
    const val MetaDataTable = "MetaData"
    const val FavouriteProductTable = "FavouriteProductTable"
}