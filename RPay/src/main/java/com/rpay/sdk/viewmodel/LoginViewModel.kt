package com.rpay.sdk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpay.sdk.extensions.getErrorResponse
import com.rpay.sdk.model.CountryListResponse
import com.rpay.sdk.model.LoginResponse
import com.rpay.sdk.network.ApiClient
import com.rpay.sdk.utils.NetworkResponse
import com.rpay.sdk.utils.SingleLiveEvent
import kotlinx.coroutines.launch

internal class LoginViewModel: ViewModel() {

    private val loginDetails: SingleLiveEvent<NetworkResponse<LoginResponse>> = SingleLiveEvent()
    private val countryList: SingleLiveEvent<NetworkResponse<CountryListResponse>> = SingleLiveEvent()

    fun login(params: HashMap<String, String>, headers: String): LiveData<NetworkResponse<LoginResponse>> {
        viewModelScope.launch {
            loginDetails.postValue(NetworkResponse.Loading())
            val response = ApiClient.getClient.login(headers, params)
            if (response.isSuccessful) {
                loginDetails.postValue(NetworkResponse.Success(response.body()!!))
            }else {
                loginDetails.postValue(NetworkResponse.ErrorResponse(getErrorResponse(response.errorBody()!!)))
            }
        }
        return loginDetails
    }

    fun countryList(): LiveData<NetworkResponse<CountryListResponse>> {
        viewModelScope.launch {
            countryList.postValue(NetworkResponse.Loading())
            val response = ApiClient.getClient.countryList()
            if (response.isSuccessful) {
                countryList.postValue(NetworkResponse.Success(response.body()!!))
            }else {
                countryList.postValue(NetworkResponse.ErrorResponse(getErrorResponse(response.errorBody()!!)))
            }
        }
        return countryList
    }

}