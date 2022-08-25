package com.androidstarter.base.validator.util

import android.view.View
import androidx.annotation.StringRes

/**
 * Created Tehrim Abbas on 25/8/2022.
 */
object ErrorMessageHelper {
    fun getStringOrDefault(
        view: View?, errorMessage: String?,
        @StringRes defaultMessage: Int
    ): String {
        return errorMessage ?: view?.context?.getString(defaultMessage)!!
    }

    fun getStringOrDefault(
        view: View?, errorMessage: String?,
        @StringRes defaultMessage: Int, value: Int
    ): String {
        return errorMessage ?: view?.context?.getString(defaultMessage, value)!!
    }

    fun getStringOrDefault(
        view: View?, errorMessage: CharSequence?,
        @StringRes defaultMessage: Int
    ): String {
        return errorMessage?.toString() ?: view?.context?.getString(defaultMessage)!!
    }

    fun getStringOrDefault(
        view: View?, errorMessage: CharSequence?,
        @StringRes defaultMessage: Int, value: Int
    ): String {
        return errorMessage?.toString() ?: view?.context?.getString(defaultMessage, value)!!
    }
}