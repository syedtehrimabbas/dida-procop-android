package com.androidstarter.base.validator.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dida.procop.R
import com.androidstarter.base.validator.rule.EmptyRule
import com.androidstarter.base.validator.rule.MaxLengthRule
import com.androidstarter.base.validator.rule.MinLengthRule

import com.androidstarter.base.validator.util.EditTextHandler
import com.androidstarter.base.validator.util.ErrorMessageHelper
import com.androidstarter.base.validator.util.ViewTagHelper

/**
 * Created Tehrim Abbas on 25/8/2022.
 */
object LengthBindings {
    @BindingAdapter(
        value = ["validateMinLength", "validateMinLengthMessage", "validateMinLengthAutoDismiss", "enableError"],
        requireAll = false
    )
    @JvmStatic
    fun bindingMinLength(
        view: TextView?,
        minLength: Int,
        errorMessage: String?,
        autoDismiss: Boolean,
        enableError: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.default_required_length_message_min, minLength
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            MinLengthRule(view, minLength, handledErrorMessage, enableError)
        )
    }

    @BindingAdapter(
        value = ["validateMaxLength", "validateMaxLengthMessage", "validateMaxLengthAutoDismiss", "enableError"],
        requireAll = false
    )
    @JvmStatic
    fun bindingMaxLength(
        view: TextView?,
        maxLength: Int,
        errorMessage: String?,
        autoDismiss: Boolean,
        enableError: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.default_required_length_message_max, maxLength
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            MaxLengthRule(view, maxLength, handledErrorMessage, enableError)
        )
    }

    @BindingAdapter(
        value = ["validateEmpty", "validateEmptyMessage", "validateEmptyAutoDismiss", "enableError"],
        requireAll = false
    )
    @JvmStatic
    fun bindingEmpty(
        view: TextView?,
        empty: Boolean,
        errorMessage: String?,
        autoDismiss: Boolean,
        enableError: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_empty_validation
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            EmptyRule(view, empty, handledErrorMessage, enableError)
        )
    }
}