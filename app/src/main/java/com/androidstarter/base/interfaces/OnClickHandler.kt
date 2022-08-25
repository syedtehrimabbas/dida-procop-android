package com.androidstarter.base.interfaces

import com.androidstarter.base.clickevents.SingleClickEvent

interface OnClickHandler {
    val clickEvent: SingleClickEvent?
    fun handlePressOnView(id: Int)
}