package com.rpay.sdk.model

import com.google.gson.annotations.SerializedName


internal data class ErrorResponse(
    @SerializedName("message")
    val message: String
)