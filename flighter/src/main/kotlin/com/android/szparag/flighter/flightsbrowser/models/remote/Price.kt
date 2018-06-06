package com.android.szparag.flighter.flightsbrowser.models.remote

import com.google.gson.annotations.SerializedName
data class Price(
    @SerializedName("value") val value: Double,
    @SerializedName("valueMainUnit") val valueMainUnit: String,
    @SerializedName("valueFractionalUnit") val valueFractionalUnit: String,
    @SerializedName("currencyCode") val currencyCode: String,
    @SerializedName("currencySymbol") val currencySymbol: String
)