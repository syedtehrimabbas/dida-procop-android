package com.androidstarter.base.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidstarter.data.sessions.SharedPreferenceManager
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    /**
     * Gets called right before the UI initialization.
     */
    protected open fun preInit(savedInstanceState: Bundle?) {
        //
    }

    /**
     * Get's called when it's the right time for you to initialize the UI elements.
     *
     * @param savedInstanceState state bundle brought from the [android.app.Activity.onCreate]
     */
    protected open fun init(savedInstanceState: Bundle?) {
        //
    }

    /**
     * Gets called right after the UI executePendingBindings.
     */
    protected open fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        //
    }

    /**
     * Gets called right after the UI initialization.
     */
    protected open fun postInit() {
        //
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val sharedPreferenceManager = SharedPreferenceManager(applicationContext)

        val local = when (sharedPreferenceManager.getValueString("language") ?: "fra") {
            "eng" -> Locale.ENGLISH
            "fra" -> Locale.FRENCH
            else -> Locale.ENGLISH
        }
        return local
    }

}