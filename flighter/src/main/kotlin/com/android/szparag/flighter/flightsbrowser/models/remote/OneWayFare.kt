package com.android.szparag.flighter.flightsbrowser.models.remote

import com.google.gson.annotations.SerializedName

data class OneWayFare(
    @SerializedName("total") val total: Int,
    @SerializedName("arrivalAirportCategories") val arrivalAirportCategories: Any,
    @SerializedName("fares") val fares: List<Fare>,
    @SerializedName("size") val size: Int
)