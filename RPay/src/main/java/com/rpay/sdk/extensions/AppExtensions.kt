package com.rpay.sdk.extensions

import com.google.gson.Gson
import com.rpay.sdk.model.ErrorResponse
import okhttp3.ResponseBody

fun getErrorResponse(response: ResponseBody): ErrorResponse {
    return Gson().fromJson(response.string(), ErrorResponse::class.java)
}