package com.rpay.sdk.core

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import com.rpay.sdk.listener.RPayListener

@SuppressLint("StaticFieldLeak")
object RPay {

    /**
     * RPay Handler - Handler for all payment related works
     */
    private var rPayHandler: RPayHandler? = null

    /**
     * Initialize payment
     */
    fun init(context: Activity, key: String) {
        when {
            key.isEmpty() -> {
                RPayLog.message("Api key should not be empty")
            }
            else -> {
                rPayHandler = RPayHandler.getInstance(context)
                rPayHandler!!.storeMerchantKey(key)
            }
        }
    }

    /**
     * Set sdk log visible or not
     */
    fun settings(logVisible: Boolean, appName: String? = null) {
        RPayLog.setLogVisible(logVisible)
        if (appName != null) {
            rPayHandler!!.setAppName(appName)
        }
    }

    /**
     * SetUp payment status listener
     */
    fun setPaymentListener(listener: RPayListener) {
        if (rPayHandler == null){
            RPayLog.message("Sdk not initialized")
        }else {
            rPayHandler!!.setPaymentListener(listener)
        }
    }

    /**
     * SetUp payment
     */
    fun makePayment(amount: Double, currencyCode: String) {
        if (rPayHandler == null){
            RPayLog.message("Sdk not initialized")
        }else {
            rPayHandler!!.makePayment(amount, currencyCode)
        }
    }

    /**
     * Get payment status listener
     */
    internal fun getListener(): RPayListener {
        return rPayHandler?.getPaymentListener()!!
    }

    /**
     * Get merchant key
     */
    internal fun getMerchantKey(): String {
        return rPayHandler?.getMerchantKey().toString()
    }

    /**
     * Store Auth Token
     */
    internal fun storeAuthToken(key: String) {
        rPayHandler?.storeAuthToken(key)
    }

    /**
     * Get Auth Token
     */
    internal fun getAuthToken(): String {
        return rPayHandler?.getAuthToken().toString()
    }

    /**
     * Get Total Amount
     */
    internal fun getTotalAmount(): String {
        return rPayHandler?.getTotalAmount().toString()
    }

    /**
     * Get Currency Code
     */
    internal fun getCurrencyCode(): String {
        return rPayHandler?.getCurrencyCode().toString()
    }

    /**
     * Get Network Status
     */
    internal fun isNetConnected(context: Context): Boolean? {
        return rPayHandler?.isConnected(context)
    }

    /**
     * Get App Name
     */
    internal fun getAppName(): String? {
        return rPayHandler?.getAppName()
    }

    /**
     * Store Login Status
     */
    internal fun setLoggedIn(key: Boolean) {
        rPayHandler?.setLoggedIn(key)
    }

    /**
     * Get Login Status
     */
    internal fun isLoggedIn(): Boolean? {
        return rPayHandler?.isLoggedIn()
    }

    /**
     * Clear All Data
     */
    internal fun clearData() {
        rPayHandler?.clearData()
    }

}