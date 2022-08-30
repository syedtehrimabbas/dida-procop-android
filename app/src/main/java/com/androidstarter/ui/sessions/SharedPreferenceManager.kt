package com.androidstarter.ui.sessions

import android.accounts.AccountManager.KEY_PASSWORD
import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.androidstarter.base.KEY_USERNAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


/**
 * To improve the performanceSharedPreferenceManager  @constructor is private.
 * you must need to  access Singlaton instanse of SharedPreferenceManager
 * i.e SharedPreferenceManager.getInstance(context)
 * @see SingletonHolder
 * */
@Singleton
class SharedPreferenceManager @Inject constructor(@ApplicationContext val context: Context) {

    private val PREFS_NAME = "PROCOP"
    private var sharedPref: SharedPreferences

    init {
        sharedPref = initializeEncryptedSharedPreferencesManager()
    }

    private fun initializeEncryptedSharedPreferencesManager(): SharedPreferences {
        val spec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
            .build()

        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(spec)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey, // masterKey created above
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
//        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
//
//        return EncryptedSharedPreferences.create(
//            PREFS_NAME,
//            masterKeyAlias,
//            context,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
    }

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status)
        editor.apply()
    }

    private fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun clearSharedPreference() {
        try {
            val editor: SharedPreferences.Editor = sharedPref.edit()
            val allEntr: Map<String, *> = sharedPref.all
            for (entry in allEntr.keys) {
                editor.remove(entry)
            }
            editor.apply()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

    fun saveEmailAndPassword(text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_USERNAME, text)
        editor.putString(KEY_PASSWORD, text)
        editor.apply()

    }

    fun getDecryptedUserName(): String? {
        return getValueString(KEY_USERNAME)
    }

    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

}
