package com.rpay.sdk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpay.sdk.extensions.getErrorResponse
import com.rpay.sdk.model.CaptureResponse
import com.rpay.sdk.model.PaymentDetailResponse
import com.rpay.sdk.network.ApiClient
import com.rpay.sdk.utils.NetworkResponse
import com.rpay.sdk.utils.SingleLiveEvent
import kotlinx.coroutines.launch

internal class PaymentScreenViewModel: ViewModel() {

    private val paymentDetails: SingleLiveEvent<NetworkResponse<PaymentDetailResponse>> = SingleLiveEvent()
    private val capturePayment: SingleLiveEvent<NetworkResponse<CaptureResponse>> = SingleLiveEvent()

    fun getPaymentDetails(headers: HashMap<String, String>, params: HashMap<String, String>): LiveData<NetworkResponse<PaymentDetailResponse>> {
        viewModelScope.launch {
            paymentDetails.postValue(NetworkResponse.Loading())
            val response = ApiClient.getClient.paymentDetails(headers, params)
            if (response.isSuccessful) {
                paymentDetails.postValue(NetworkResponse.Success(response.body()!!))
            }else {
                paymentDetails.postValue(NetworkResponse.ErrorResponse(getErrorResponse(response.errorBody()!!)))
            }
        }
        return paymentDetails
    }

    fun capturePayment(headers: HashMap<String, String>, params: HashMap<String, String>): LiveData<NetworkResponse<CaptureResponse>> {
        viewModelScope.launch {
            capturePayment.postValue(NetworkResponse.Loading())
            val response = ApiClient.getClient.capturePayment(headers, params)
            if (response.isSuccessful) {
                capturePayment.postValue(NetworkResponse.Success(response.body()!!))
            }else {
                capturePayment.postValue(NetworkResponse.ErrorResponse(getErrorResponse(response.errorBody()!!)))
            }
        }
        return capturePayment
    }

}