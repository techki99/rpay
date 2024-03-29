package com.rpay.sdk.core

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.rpay.sdk.listener.RPayListener
import com.rpay.sdk.utils.Session
import com.rpay.sdk.view.activity.RPayHomeScreen
import java.util.*

@SuppressLint("StaticFieldLeak")
internal class RPayHandler(
    /**
     * View context
     */
    private var context: Activity
) {

    /**
     * session to store values
     */
    private var session: Session = Session.getInstance(context)

    companion object {
        /**
         * RPay Handler - Handler for all payment related works
         */
        private var rPayHandler: RPayHandler? = null

        /**
         * Initialize Handler
         */
        fun getInstance(context: Activity): RPayHandler {
            if (rPayHandler == null) {
                rPayHandler = RPayHandler(context)
            }
            return rPayHandler as RPayHandler
        }
    }

    /**
     * RPay Listener - Listen payment status
     */
    private lateinit var rPayListener: RPayListener


    /**
     * Store merchant key
     */
    fun storeMerchantKey(key: String) {
        session.setMerchantKey(key)
    }

    /**
     * Get merchant key
     */
    fun getMerchantKey(): String {
        return session.getMerchantKey().toString()
    }

    /**
     * Store Auth Token
     */
    fun storeAuthToken(key: String) {
        session.setAuthToken(key)
    }

    /**
     * Get Auth Token
     */
    fun getAuthToken(): String {
        return session.getAuthToken().toString()
    }

    /**
     * Get Total Amount
     */
    fun getTotalAmount(): String {
        return session.getTotalAmount().toString()
    }

    /**
     * Get Currency Code
     */
    fun getCurrencyCode(): String {
        return session.getCurrencyCode().toString()
    }

    /**
     * SetUp payment listener
     */
    fun setPaymentListener(rPayListener: RPayListener) {
        this.rPayListener = rPayListener
    }

    /**
     * SetUp App Name
     */
    fun setAppName(name: String) {
        session.setAppName(name)
    }

    /**
     * Get App Name
     */
    fun getAppName(): String {
        return session.getAppName()
    }

    /**
     * SetUp Login Status
     */
    fun setLoggedIn(status: Boolean) {
        session.setLoggedIn(status)
    }

    /**
     * Get Login Status
     */
    fun isLoggedIn(): Boolean {
        return session.isLoggedIn()
    }

    /**
     * Clear All Data
     */
    fun clearData() {
        return session.clearData()
    }

    /**
     * SetUp payment listener
     */
    fun getPaymentListener(): RPayListener {
        return rPayListener
    }

    /**
     * SetUp Payment screen
     */
    fun makePayment(amount: Double, currencyCode: String) {
        when {
            amount.toString().isEmpty() -> {
                RPayLog.message("Price should not be empty")
            }
            amount == 0.0 -> {
                RPayLog.message("Price should not be 0")
            }
            currencyCode.isEmpty() -> {
                RPayLog.message("Currency should not be empty")
            }
            else -> {
                val intent = Intent(context, RPayHomeScreen::class.java)
                session.setCurrencyCode(currencyCode)
                session.setTotalAmount(String.format(Locale.ENGLISH, "%.2f", amount))
                context.startActivity(intent)
            }
        }
    }

    /**
     * Check Network Connection
     */
    fun isConnected(context: Context): Boolean {
        try {
            val conMgr =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = conMgr.getNetworkCapabilities(
                    conMgr.activeNetwork
                )
                capabilities != null && (capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                ) || capabilities
                    .hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            } else conMgr.activeNetworkInfo != null &&
                    conMgr.activeNetworkInfo!!.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}