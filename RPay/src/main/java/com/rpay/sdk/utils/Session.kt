package com.rpay.sdk.utils


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


internal object Session {

    private lateinit var userDetails: SharedPreferences
    private lateinit var userDetailsEditor: SharedPreferences.Editor

    private lateinit var appDetails: SharedPreferences
    private lateinit var appDetailsEditor: SharedPreferences.Editor

    var session: Session? = null

    @SuppressLint("CommitPrefEdits")
    fun getInstance(context: Context): Session {

        if (session == null){
            session = Session
        }

        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        userDetails = EncryptedSharedPreferences.create(
                context,
                "rpayUser",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        appDetails = EncryptedSharedPreferences.create(
            context,
            "rpayApp",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        appDetailsEditor = appDetails.edit()
        userDetailsEditor = userDetails.edit()

        return session as Session
    }

    fun setMerchantKey(key: String) {
        appDetailsEditor.putString("merchantKey", key).commit()
    }

    fun getMerchantKey(): String {
        return appDetails.getString("merchantKey", "").toString()
    }

    fun setAuthToken(key: String) {
        userDetailsEditor.putString("AuthToken", key).commit()
    }

    fun getAuthToken(): String {
        return userDetails.getString("AuthToken", "").toString()
    }

    fun setTotalAmount(key: String) {
        appDetailsEditor.putString("amount", key).commit()
    }

    fun getTotalAmount(): String {
        return appDetails.getString("amount", "").toString()
    }

    fun setCurrencyCode(key: String) {
        appDetailsEditor.putString("currencyCode", key).commit()
    }

    fun getCurrencyCode(): String {
        return appDetails.getString("currencyCode", "").toString()
    }

    fun setAppName(key: String) {
        appDetailsEditor.putString("appName", key).commit()
    }

    fun getAppName(): String {
        return appDetails.getString("appName", "").toString()
    }

    fun setLoggedIn(key: Boolean) {
        userDetailsEditor.putBoolean("loggedIn", key).commit()
    }

    fun isLoggedIn(): Boolean {
        return userDetails.getBoolean("loggedIn", false)
    }

    fun clearData() {
        userDetailsEditor.clear().commit()
    }
}