package com.androidstarter.data.model

import android.os.Parcelable
import com.androidstarter.data.cart.models.CartMetaData
import com.androidstarter.ui.cart.billing.data.BillingAddressData
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderRequest(
    @SerializedName("payment_method")
    val paymentMethod: String = "Paypal",
    @SerializedName("payment_method_title")
    val paymentMethodTitle: String = "Paypal",
    @SerializedName("set_paid")
    val setPaid: Boolean = false,
    @SerializedName("customer_id")
    val customerId: Int = 0,
    @SerializedName("billing")
    val billing: BillingAddressData?,
    @SerializedName("shipping")
    val shipping: BillingAddressData?,
    @SerializedName("line_items")
    var lineItems: ArrayList<LineItem> = arrayListOf(),
    @SerializedName("shipping_lines")
    var shippingLines: ArrayList<ShippingLine> = arrayListOf()
) : Parcelable

@Parcelize
data class LineItem(
    @SerializedName("product_id")
    var productId: Int = 0,
    @SerializedName("quantity")
    var quantity: Int = 0,
    @SerializedName("meta_data")
    var metaDate: ArrayList<CartMetaData> = arrayListOf()
) : Parcelable

@Parcelize
data class ShippingLine(
    @SerializedName("method_id")
    val methodId: String = "flat_rate",
    @SerializedName("method_title")
    val methodTitle: String = "Flat Rate",
    @SerializedName("total")
    val total: String
) : Parcelable

