package com.rpay.sdk.utils


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


internal object Session {

    private lateinit var details: SharedPreferences
    private lateinit var detailsEditor: SharedPreferences.Editor

    var session: Session? = null

    @SuppressLint("CommitPrefEdits")
    fun getInstance(context: Context): Session {

        if (session == null){
            session = Session
        }

        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        details = EncryptedSharedPreferences.create(
                context,
                "rpay",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        detailsEditor = details.edit()

        return session as Session
    }

    fun setMerchantKey(key: String) {
        detailsEditor.putString("merchantKey", key).commit()
    }

    fun getMerchantKey(): String {
        return details.getString("merchantKey", "").toString()
    }

    fun setAuthToken(key: String) {
        detailsEditor.putString("AuthToken", key).commit()
    }

    fun getAuthToken(): String {
        return details.getString("AuthToken", "").toString()
    }

    fun setTotalAmount(key: String) {
        detailsEditor.putString("amount", key).commit()
    }

    fun getTotalAmount(): String {
        return details.getString("amount", "").toString()
    }

    fun setCurrencyCode(key: String) {
        detailsEditor.putString("currencyCode", key).commit()
    }

    fun getCurrencyCode(): String {
        return details.getString("currencyCode", "").toString()
    }

    fun setAppName(key: String) {
        detailsEditor.putString("appName", key).commit()
    }

    fun getAppName(): String {
        return details.getString("appName", "").toString()
    }
}