package com.android.szparag.flighter.selectdeparture.models

data class AirportModel(
    val cityName: String,
    val cityCode: String,
    val countryName: String,
    val countryCode: String,
    val airportName: String,
    val airportCode: String
)
data class AirportDTO(
    val EPKK: EPKK,
    val EPKP: EPKP
)

data class EPKP(
    val city: String,
    val country: String,
    val elevation: Int,
    val iata: String,
    val icao: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String,
    val tz: String
)

data class EPKK(
    val city: String,
    val country: String,
    val elevation: Int,
    val iata: String,
    val icao: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String,
    val tz: String
)