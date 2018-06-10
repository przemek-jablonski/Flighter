package com.android.szparag.flighter.selectdeparture.states

sealed class SelectDepartureIntent {

  data class TextSearchIntent(val searchInput: String): SelectDepartureIntent()

  class GpsSearchIntent: SelectDepartureIntent()

  data class AirportSelectionIntent(val airportIataCode: String, val airportName: String) : SelectDepartureIntent()

}