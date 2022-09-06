package com.androidstarter.ui.fq

import androidx.lifecycle.MutableLiveData
import com.androidstarter.base.validator.IValidator
import com.androidstarter.base.validator.Validator
import com.androidstarter.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaqVM @Inject constructor(
    override val viewState: FaqState,
    override var validator: Validator?,
) : HiltBaseViewModel<IFaq.State>(), IFaq.ViewModel, IValidator {
    val faqDataList: MutableLiveData<MutableList<FaqData>> =
        MutableLiveData()

    init {
        val list = arrayListOf<FaqData>()
        list.add(
            FaqData(
                "How do I create my account on the PROCOP eShop?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )
        list.add(
            FaqData(
                "How do I select the items I want to order?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )
        list.add(
            FaqData(
                "Are all the items visible on the site available?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )
        list.add(
            FaqData(
                "What is the minimum amount of sale quantity?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )
        list.add(
            FaqData(
                "How do I create my account on the PROCOP eShop?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )
        list.add(
            FaqData(
                "How do I select the items I want to order?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )
        list.add(
            FaqData(
                "Are all the items visible on the site available?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )
        list.add(
            FaqData(
                "What is the minimum amount of sale quantity?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )
        list.add(
            FaqData(
                "How do I create my account on the PROCOP eShop?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )
        list.add(
            FaqData(
                "How do I select the items I want to order?",
                """
                If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.
                The “Email” field (your login), is essential to be recognized during your first connection with your personal password.
                
                If you are already a PROCOP customer through the old website, you will have to recreate your account.
                """.trimIndent()
            )
        )

        faqDataList.value = list
    }
}