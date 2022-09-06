package com.androidstarter.ui.interfaces

import me.gilo.woodroid.models.Product
import me.gilo.woodroid.models.ProductAttribute

interface ProductUpdateListener {
    fun onProductUpdate(product: Product) {

    }

    fun onAttributeUpdate(attribute: ProductAttribute,selectedValue:String) {

    }
}