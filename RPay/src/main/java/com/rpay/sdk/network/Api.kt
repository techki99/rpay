package com.rpay.sdk.network

import com.rpay.sdk.model.*
import retrofit2.Response
import retrofit2.http.*

internal interface Api {

    @GET("country/list")
    suspend fun countryList(): Response<CountryListResponse>

    @POST("sdk/api/customer/login")
    suspend fun login(@Header("secret_key") headers: String, @Body params: HashMap<String, String>): Response<LoginResponse>

    @POST("sdk/api/customer/verify-otp")
    suspend fun verifyOTP(@HeaderMap headers: HashMap<String, String>, @Body params: HashMap<String, String>): Response<OTPResponse>

    @POST("sdk/api/customer/balance-validate")
    suspend fun paymentDetails(@HeaderMap headers: HashMap<String, String>, @Body params: HashMap<String, String>): Response<PaymentDetailResponse>

    @POST("sdk/api/customer/capture-transaction")
    suspend fun capturePayment(@HeaderMap headers: HashMap<String, String>, @Body params: HashMap<String, String>): Response<CaptureResponse>

}