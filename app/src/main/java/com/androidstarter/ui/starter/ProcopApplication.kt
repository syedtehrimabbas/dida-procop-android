package com.androidstarter.ui.starter

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.androidstarter.data.sessions.SharedPreferenceManager
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class ProcopApplication : Application() {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate() {
        super.onCreate()
        sharedPreferenceManager = SharedPreferenceManager(applicationContext)

        val config = CheckoutConfig(
            application = this,
            clientId = "ASjGtKLp47e24pRX3ht4RjOcRCd-ChjhHphoJR-tL-ozCxqfbnNY9kQSzA9JxE3OZs1OkbXXxysNtwfB",
            environment = Environment.LIVE,
//            returnUrl = "${BuildConfig.APPLICATION_ID}://paypalpay",
            currencyCode = CurrencyCode.EUR,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )
        PayPalCheckout.setConfig(config)
        setLocale()
    }

    private fun setLocale() {
        val locale: Locale = getLocale()
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }

    private fun getLocale(): Locale {
        val local = when (sharedPreferenceManager.getValueString("language") ?: "fra") {
            "eng" -> Locale.ENGLISH
            "fra" -> Locale.FRENCH
            else -> Locale.ENGLISH
        }
        return local
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLocale()
    }
}