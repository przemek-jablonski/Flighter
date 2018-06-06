package com.android.szparag.flighter.flightsbrowser.models.remote

import com.google.gson.annotations.SerializedName
data class ArrivalAirport(
    @SerializedName("iataCode") val iataCode: String,
    @SerializedName("name") val name: String,
    @SerializedName("seoName") val seoName: String,
    @SerializedName("countryName") val countryName: String
)