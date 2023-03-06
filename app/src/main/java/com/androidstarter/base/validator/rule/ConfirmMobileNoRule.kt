package com.androidstarter.base.validator.rule

import android.widget.TextView
import androidx.annotation.Keep
import com.google.android.material.textfield.TextInputEditText
import com.dida.procop.R
import com.androidstarter.base.extensions.getDrawable
import com.androidstarter.base.extensions.isEmpty
import com.androidstarter.base.validator.util.EditTextHandler.removeError

/**
 * Created Tehrim Abbas on 25/8/2022.
 */
@Keep
class ConfirmMobileNoRule(
    view: TextInputEditText?,
    value: TextInputEditText?,
    errorMessage: String?,
    showErrorMessage: Boolean
) : Rule<TextView?, TextView?>(
    view,
    value,
    errorMessage,
    showErrorMessage
) {
    override fun isValid(view: TextView?): Boolean {
        if (value == null) return false
        val value1 = view?.text.toString()
        val value2 = value?.text.toString()
        return !isEmpty(value1) && value1.trim { it <= ' ' } == value2.trim { it <= ' ' }
    }

    override fun onValidationSucceeded(view: TextView?) {
        view?.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                compoundDrawables[0],
                compoundDrawables[1],
                getDrawable(R.drawable.ic_path),
                compoundDrawables[3]
            )
        }
        removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
        view?.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                compoundDrawables[0],
                compoundDrawables[1],
                null,
                compoundDrawables[3]
            )
        }
    }
}