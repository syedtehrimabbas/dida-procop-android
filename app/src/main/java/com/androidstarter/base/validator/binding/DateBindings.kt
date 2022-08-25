package com.androidstarter.base.validator.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.androidstarter.R
import com.androidstarter.base.validator.rule.DateRule
import com.androidstarter.base.validator.util.EditTextHandler
import com.androidstarter.base.validator.util.ErrorMessageHelper
import com.androidstarter.base.validator.util.ViewTagHelper

/**
 * Created Tehrim Abbas on 25/8/2022.
 */
object DateBindings {
    @BindingAdapter(
        value = ["validateDate", "validateDateMessage", "validateDateAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingDate(
        view: TextView?,
        pattern: String?,
        errorMessage: String?,
        autoDismiss: Boolean,
        errorEnabled: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_date_validation
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            DateRule(view, pattern, handledErrorMessage, errorEnabled)
        )
    }
}