package com.rpay.sdk.model

import com.google.gson.annotations.SerializedName

data class CountryListResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    var data: Data
) {

    data class Data(
        @SerializedName("countryList")
        var countryList: List<CountryList>,
        @SerializedName("total_count")
        var total_count: Int
    )

    data class CountryList(
        @SerializedName("id")
        var id: Int,
        @SerializedName("country_name")
        val country_name: String,
        @SerializedName("country_code")
        val country_code: String,
        @SerializedName("country_flag")
        val country_flag: String,
        @SerializedName("phone_code")
        val phone_code: String,
        @SerializedName("currency_name")
        val currency_name: String,
        @SerializedName("currency_code")
        val currency_code: String,
        @SerializedName("currency_symbol")
        val currency_symbol: String,
        @SerializedName("time_zone")
        val time_zone: String,
        @SerializedName("iso_code")
        val iso_code: String,
        @SerializedName("status")
        var status: Int
    )
}