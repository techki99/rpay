package com.rpay.sdk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpay.sdk.extensions.getErrorResponse
import com.rpay.sdk.model.OTPResponse
import com.rpay.sdk.network.ApiClient
import com.rpay.sdk.utils.NetworkResponse
import com.rpay.sdk.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class OTPScreenViewModel: ViewModel() {

    private val otpStatus: SingleLiveEvent<NetworkResponse<OTPResponse>> = SingleLiveEvent()

    fun getOTPStatus(params: HashMap<String, String>, headers: HashMap<String, String>): LiveData<NetworkResponse<OTPResponse>> {
        viewModelScope.launch {
            otpStatus.postValue(NetworkResponse.Loading())
            val response = ApiClient.getClient.verifyOTP(headers, params)
            if (response.isSuccessful){
                otpStatus.postValue(NetworkResponse.Success(response.body()!!))
            }else{
                otpStatus.postValue(NetworkResponse.ErrorResponse(getErrorResponse(response.errorBody()!!)))
            }
        }
        return otpStatus
    }
}