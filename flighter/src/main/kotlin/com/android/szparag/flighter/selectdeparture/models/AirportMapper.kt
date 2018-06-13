package com.android.szparag.flighter.selectdeparture.models

import com.android.szparag.flighter.common.location.WorldCoordinates
import com.android.szparag.flighter.selectdeparture.models.local.AirportModel
import com.android.szparag.flighter.selectdeparture.models.remote.AirportDTO

fun AirportDTO.mapToModel() = AirportModel(
    airportName = name,
    airportIataCode = iata,
    address = "$city, ${if(state.isNotEmpty()) "$state, " else ""}$country",
    countryCode = country,
    coordinates = WorldCoordinates(lat, lon)
)