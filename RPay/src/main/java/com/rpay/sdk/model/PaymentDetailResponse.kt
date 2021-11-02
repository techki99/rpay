package com.rpay.sdk.model

import com.google.gson.annotations.SerializedName

data class PaymentDetailResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: String,
    @SerializedName("data")
    var data: Data
) {

    data class Data(
        @SerializedName("req_amount")
        val req_amount: String,
        @SerializedName("req_currency")
        val req_currency: String,
        @SerializedName("deduct_amount")
        val deduct_amount: String,
        @SerializedName("deductable_amount")
        val deductable_amount: String,
        @SerializedName("deductable_currency")
        val deductable_currency: String,
        @SerializedName("site_fee")
        val site_fee: String,
        @SerializedName("api_user_currency")
        val api_user_currency: String,
        @SerializedName("api_user_balance")
        val api_user_balance: String
    )
}