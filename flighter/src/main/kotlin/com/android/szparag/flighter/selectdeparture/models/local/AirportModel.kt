package com.android.szparag.flighter.selectdeparture.models.local

import com.android.szparag.flighter.common.location.WorldCoordinates

data class AirportModel(
    val airportName: String,
    val airportIataCode: String,
    val address: String,
    val countryCode: String,
    val coordinates: WorldCoordinates
)