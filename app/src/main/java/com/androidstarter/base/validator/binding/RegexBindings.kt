package com.androidstarter.base.validator.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dida.procop.R
import com.androidstarter.base.validator.rule.RegexRule
import com.androidstarter.base.validator.util.EditTextHandler
import com.androidstarter.base.validator.util.ErrorMessageHelper
import com.androidstarter.base.validator.util.ViewTagHelper

/**
 * Created Tehrim Abbas on 25/8/2022.
 */
object RegexBindings {
    @BindingAdapter(
        value = ["validateRegex", "validateRegexMessage", "validateRegexAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingRegex(
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
            errorMessage, R.string.error_message_regex_validation
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            RegexRule(view, pattern, handledErrorMessage, errorEnabled)
        )
    }
}