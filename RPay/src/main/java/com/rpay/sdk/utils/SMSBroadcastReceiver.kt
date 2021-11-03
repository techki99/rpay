package com.rpay.sdk.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.rpay.sdk.listener.OTPReceiverListener
import java.util.regex.Pattern

internal class SMSBroadcastReceiver: BroadcastReceiver() {

    private var otpReceiver: OTPReceiverListener? = null

    fun initOTPListener(receiver: OTPReceiverListener) {
        this.otpReceiver = receiver
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            if (extras != null) {
                val status = extras.get(SmsRetriever.EXTRA_STATUS) as Status
                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                        val pattern = Pattern.compile("\\d{4}")
                        val matcher = pattern.matcher(message)
                        if (matcher.find()) {
                            otpReceiver?.onOTPReceived(matcher.group(0)!!)
                        }else {
                            otpReceiver?.onOTPFailed("Failed to detect")
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        otpReceiver?.onOTPFailed("Failed to detect")
                    }
                }
            }else {
                otpReceiver?.onOTPFailed("Failed to detect")
            }
        }
    }
}