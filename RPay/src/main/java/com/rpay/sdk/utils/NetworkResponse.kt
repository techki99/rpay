package com.rpay.sdk.utils

import com.rpay.sdk.model.ErrorResponse

sealed class NetworkResponse<T>(val data: T? = null, val message: String? = null, val errorResponse: com.rpay.sdk.model.ErrorResponse? = null) {
    class Success<T>(data: T) : NetworkResponse<T>(data=data, null,null)
    class Error<T>(message: String) : NetworkResponse<T>(null, message,null)
    class Loading<T>() : NetworkResponse<T>()
    class ErrorResponse<T>(errorResponse: com.rpay.sdk.model.ErrorResponse) : NetworkResponse<T>(null, null, errorResponse = errorResponse)
}