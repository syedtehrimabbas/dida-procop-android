package com.androidstarter.data.source.remote

import com.androidstarter.data.BaseResponse
import com.androidstarter.data.model.LineItem
import com.google.gson.annotations.SerializedName
import me.gilo.woodroid.models.*
import java.util.*


data class OrderResponse(
    var id: Int = 0,
    @SerializedName("number")
    var orderNumber: String,

    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("date_created")
    var dateCreated: Date,

    @SerializedName("updated_at")
    var updatedAt: String,

    @SerializedName("completed_at")
    var completedAt: String,
    var status: String,
    var currency: String,
    var total: String,
    var subtotal: String,

    @SerializedName("total_line_items_quantity")
    var totalLineItemsQuantity: Int = 0,

    @SerializedName("total_tax")
    var totalTax: String,

    @SerializedName("total_shipping")
    var totalShipping: String,

    @SerializedName("cart_tax")
    var cartTax: String,

    @SerializedName("shipping_tax")
    var shippingTax: String,

    @SerializedName("total_discount")
    var totalDiscount: String,

    @SerializedName("shipping_methods")
    var shippingMethods: String,

    @SerializedName("payment_details")
    var paymentDetails: PaymentDetails,

    @SerializedName("billing")
    var billingAddress: BillingAddress,

    @SerializedName("shipping")
    var shippingAddress: ShippingAddress,
    var note: String,

    @SerializedName("customer_ip")
    var customerIp: String,

    @SerializedName("customer_user_agent")
    var customerUserAgent: String,

    @SerializedName("customer_id")
    var customerId: Int? = null,

    @SerializedName("view_order_url")
    var viewOrderUrl: String,

    @SerializedName("line_items")
    var lineItems: MutableList<LineItem> = ArrayList(),

    @SerializedName("shipping_lines")
    var shippingLines: List<ShippingLine> = ArrayList(),

    @SerializedName("tax_lines")
    var taxLines: List<TaxLine> = ArrayList(),

    @SerializedName("fee_lines")
    var feeLines: List<FeeLine> = ArrayList(),

    @SerializedName("coupon_lines")
    var couponLines: List<Any> = ArrayList(),
    var customer: Customer,
) : BaseResponse() {
    fun addLineItem(lineItem: LineItem) {
        lineItems.add(lineItem)
    }
}
