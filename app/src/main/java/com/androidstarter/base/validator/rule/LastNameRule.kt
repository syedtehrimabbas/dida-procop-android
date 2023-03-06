package com.androidstarter.base.validator.rule

import android.view.View
import android.widget.TextView
import androidx.annotation.Keep
import com.dida.procop.R

import com.androidstarter.base.validator.util.EditTextHandler
@Keep
class LastNameRule(view: TextView, errorMessage: String, errorEnabled: Boolean) :
    TypeRule(view, FieldType.LastName, errorMessage, errorEnabled) {
    override fun isValid(view: TextView?): Boolean {
        view?.let {
            val username = it.text.toString()
            val expression =
                "^[a-zA-Z]{1,100}\$"
            return it.visibility == View.GONE || username.matches(expression.toRegex())
        }
        return false
    }

    override fun onValidationSucceeded(view: TextView?) {
        super.onValidationSucceeded(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)
                    ?.apply { setEndIconDrawable(R.drawable.ic_path) }
            }
            //view?.let { removeError(it) }
        }
//        view?.let { EditTextHandler.removeError(it) }
    }

    override fun onValidationFailed(view: TextView?) {
        super.onValidationFailed(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)?.apply {
                    endIconDrawable = null
                }
            }
        }

    }
}