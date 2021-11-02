package com.rpay.sdk.model

import com.google.gson.annotations.SerializedName

internal data class CaptureResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    var data: Data
) {

    data class Data(
        @SerializedName("entity")
        val entity: String,
        @SerializedName("TransactionId")
        val TransactionId: String,
        @SerializedName("amount")
        var amount: Double,
        @SerializedName("currency")
        val currency: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("created_at")
        val created_at: String
    )
}