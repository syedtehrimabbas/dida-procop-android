package com.androidstarter.base.validator.binding

import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.dida.procop.R
import com.androidstarter.base.validator.rule.TypeRule
import com.androidstarter.base.validator.util.EditTextHandler
import com.androidstarter.base.validator.util.ErrorMessageHelper
import com.androidstarter.base.validator.util.ViewTagHelper

/**
 * Created Tehrim Abbas on 25/8/2022.
 */

object TypeBindings {
    @JvmStatic
    @BindingAdapter(
        value = ["validateType", "validateTypeMessage", "validateTypeAutoDismiss", "enableError"],
        requireAll = false
    )
    fun bindingTypeValidation(
        view: TextView?,
        fieldTypeText: String,
        errorMessage: String?,
        autoDismiss: Boolean,
        enableError: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        try {

            val fieldType =
                getFieldTypeByText(fieldTypeText)

            val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
                view,
                errorMessage, fieldType.errorMessageId
            )
            ViewTagHelper.appendValue(
                R.id.validator_rule,
                view,
                fieldType.instantiate(view, handledErrorMessage, enableError)
            )
        } catch (ignored: Exception) {
            Toast.makeText(view?.context, ignored.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun getFieldTypeByText(fieldTypeText: String): TypeRule.FieldType {
        var fieldType =
            TypeRule.FieldType.None
        for (type in TypeRule.FieldType.values()) {
            if (type.toString().equals(fieldTypeText, ignoreCase = true)) {
                fieldType = type
                break
            }
        }
        return fieldType
    }
}