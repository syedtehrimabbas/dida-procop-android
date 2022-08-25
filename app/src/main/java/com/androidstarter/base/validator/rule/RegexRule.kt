package com.androidstarter.base.validator.rule

import android.widget.TextView
import androidx.annotation.Keep
import com.androidstarter.base.extensions.getValueWithoutComaAndSpace
import com.androidstarter.base.validator.util.EditTextHandler

/**
 * Created Tehrim Abbas on 25/8/2022.
 */
@Keep
class RegexRule(
    view: TextView?,
    value: String?,
    errorMessage: String?,
    showErrorMessage: Boolean
) : Rule<TextView?, String?>(
    view,
    value,
    errorMessage,
    showErrorMessage
) {
    override fun isValid(view: TextView?): Boolean {
        return view?.text.toString().getValueWithoutComaAndSpace()?.replace(" ", "")
            ?.matches(value?.toRegex()!!) ?: false
    }

    override fun onValidationSucceeded(view: TextView?) {
        EditTextHandler.removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
        if (errorEnabled) EditTextHandler.setError(view, errorMessage)
    }
}