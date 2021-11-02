package com.rpay.sdk.model

import com.google.gson.annotations.SerializedName

data class OTPResponse (
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: String
)