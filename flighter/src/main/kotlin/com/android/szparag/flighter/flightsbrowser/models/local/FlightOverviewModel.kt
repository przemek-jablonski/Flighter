package com.android.szparag.flighter.flightsbrowser.models.local

import com.android.szparag.myextensionsbase.UnixTimestamp

data class FlightOverviewModel(
    val arrivalAirportName: String,
    val departureAirportName: String,
    val arrivalDate: UnixTimestamp,
    val departureDate: UnixTimestamp
)
