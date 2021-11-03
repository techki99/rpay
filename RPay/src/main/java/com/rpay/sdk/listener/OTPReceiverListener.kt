package com.rpay.sdk.listener

internal interface OTPReceiverListener {
    fun onOTPReceived(otp: String)

    fun onOTPFailed(error: String)
}