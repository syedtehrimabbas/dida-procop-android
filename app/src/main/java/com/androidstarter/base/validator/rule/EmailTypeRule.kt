package com.androidstarter.base.validator.rule

import android.util.Patterns
import android.widget.TextView
import androidx.annotation.Keep
import com.dida.procop.R
import com.androidstarter.base.validator.util.EditTextHandler
import com.androidstarter.base.validator.util.EditTextHandler.removeError

/**
 * Created Tehrim Abbas on 25/8/2022.
 */
@Keep
class EmailTypeRule(
    view: TextView?,
    errorMessage: String?,
    errorEnabled: Boolean
) : TypeRule(
    view,
    FieldType.Email,
    errorMessage,
    errorEnabled
) {
    override fun isValid(view: TextView?): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(view?.text).matches()
    }

    override fun onValidationSucceeded(view: TextView?) {
        super.onValidationSucceeded(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)
                    ?.apply { setEndIconDrawable(R.drawable.ic_path) }
            }
            removeError(view)
        }
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
       // if (errorEnabled) setError(view, errorMessage)
    }
}