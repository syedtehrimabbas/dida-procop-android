package com.androidstarter.base.validator.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.androidstarter.R
import com.androidstarter.base.validator.rule.ConfirmMobileNoRule
import com.androidstarter.base.validator.util.EditTextHandler
import com.androidstarter.base.validator.util.ErrorMessageHelper
import com.androidstarter.base.validator.util.ViewTagHelper

/**
 * Created Tehrim Abbas on 25/8/2022.
 */
object PasswordBindings {
    @BindingAdapter(
        value = ["validatePassword", "validatePasswordMessage", "validatePasswordAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingPassword(
        view: TextInputEditText?,
        comparableView: TextInputEditText?,
        errorMessage: String?,
        autoDismiss: Boolean,
        errorEnabled: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_not_equal_password
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule, view,
            ConfirmMobileNoRule(view, comparableView, handledErrorMessage, errorEnabled)
        )
    }

    @BindingAdapter(
        value = ["validateIban", "validateIbanMessage", "validateIbanAutoDismiss"],
        requireAll = false
    )
    @JvmStatic
    fun bindingIban(
        view: TextInputEditText?,
        comparableView: TextInputEditText?,
        errorMessage: String?,
        autoDismiss: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_not_equal_phone
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule, view,
            ConfirmMobileNoRule(view, comparableView, handledErrorMessage, false)
        )
    }
}