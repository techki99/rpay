package com.rpay.sdk.model

import com.google.gson.annotations.SerializedName

internal data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: String,
    @SerializedName("data")
    var data: Data
) {

    data class Data(
        @SerializedName("auth_token")
        val auth_token: String,
        @SerializedName("otp")
        var otp: Int,
        @SerializedName("sms_option")
        val sms_option: String
    )
}