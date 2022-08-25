package com.androidstarter.base.validator.rule

import android.webkit.URLUtil
import android.widget.TextView
import androidx.annotation.Keep
import com.androidstarter.base.validator.util.EditTextHandler

/**
 * Created Tehrim Abbas on 25/8/2022.
 */
@Keep
class UrlTypeRule(
    view: TextView?,
    value: FieldType?,
    errorMessage: String?,
    errorEnabled: Boolean
) : TypeRule(view, value, errorMessage, errorEnabled) {
    override fun isValid(view: TextView?): Boolean {
        return URLUtil.isValidUrl(view?.text.toString())
    }

    override fun onValidationSucceeded(view: TextView?) {
        super.onValidationSucceeded(view)
        EditTextHandler.removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
        super.onValidationFailed(view)
        if (errorEnabled) EditTextHandler.setError(view, errorMessage)
    }
}
