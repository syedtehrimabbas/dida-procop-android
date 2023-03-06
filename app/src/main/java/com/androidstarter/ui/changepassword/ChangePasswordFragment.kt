package com.androidstarter.ui.changepassword

import android.app.Dialog
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.dida.procop.BR
import com.dida.procop.R
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.dida.procop.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment :
    BaseNavViewModelFragment<FragmentChangePasswordBinding, IChangePassword.State, ChangePasswordVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: ChangePasswordVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_change_password
    override fun toolBarVisibility(): Boolean = true
    override fun onClick(id: Int) {
        when (id) {
            R.id.confirmButton -> {
                passwordUpdatePopup(
                    "Password Updated!", "Your password has been changed successfully!",
                    "Ok"
                )
            }
        }
    }

    private fun passwordUpdatePopup(title: String?, description: String?, confirmText: String?) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_password_updated)
        dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        val questionText = dialog.findViewById<TextView>(R.id.questionText)
        val detailText = dialog.findViewById<TextView>(R.id.detailText)
        val confirmBtn = dialog.findViewById<TextView>(R.id.okBtn)
        questionText.text = title
        detailText.text = description
        confirmBtn.text = confirmText
        confirmBtn.setOnClickListener { v: View? ->
            dialog.dismiss()
        }
        dialog.show()
    }
}