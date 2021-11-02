package com.rpay.sdk.core

import android.annotation.SuppressLint
import android.app.Activity
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
        RPayHandler.setAppName(appName!!)
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
    fun makePayment(amount: String, currencyCode: String) {
        if (rPayHandler == null){
            RPayLog.message("Sdk not initialized")
        }else {
            rPayHandler!!.makePayment(amount, currencyCode)
        }
    }

    /**
     * Get payment status listener
     */
    fun getListener(): RPayListener {
        return rPayHandler?.getPaymentListener()!!
    }

}