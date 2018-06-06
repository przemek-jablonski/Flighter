package com.android.szparag.flighter.flightsbrowser.models.remote

import com.google.gson.annotations.SerializedName
data class Outbound(
    @SerializedName("departureAirport") val departureAirport: DepartureAirport,
    @SerializedName("arrivalAirport") val arrivalAirport: ArrivalAirport,
    @SerializedName("departureDate") val departureDate: String,
    @SerializedName("arrivalDate") val arrivalDate: String,
    @SerializedName("price") val price: Price,
    @SerializedName("sellKey") val sellKey: Any
)