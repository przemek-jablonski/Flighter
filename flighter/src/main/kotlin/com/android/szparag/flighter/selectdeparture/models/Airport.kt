package com.android.szparag.flighter.selectdeparture.models

import com.android.szparag.myextensionsbase.invalidDoubleValue
import com.android.szparag.myextensionsbase.invalidIntValue
import com.android.szparag.myextensionsbase.invalidStringValue
import com.google.firebase.database.IgnoreExtraProperties

//cannot really do data class here, because Firebase Database can't handle that stuff :/
@IgnoreExtraProperties
class AirportDTO() {
  var city: String = invalidStringValue()
  var country: String = invalidStringValue()
  var elevation: Int = invalidIntValue()
  var iata: String = invalidStringValue()
  var icao: String = invalidStringValue()
  var lat: Double = invalidDoubleValue()
  var lon: Double = invalidDoubleValue()
  var name: String = invalidStringValue()
  var state: String = invalidStringValue()
  var tz: String = invalidStringValue()

  override fun toString() =
    "AirportDTO(city='$city', country='$country', elevation=$elevation, iata='$iata', icao='$icao', lat=$lat, lon=$lon, name='$name', state='$state', tz='$tz')"

}

data class AirportModel(
    val airportName: String,
    val airportIataCode: String,
    val address: String
)

fun AirportDTO.mapToModel() = AirportModel(
    airportName = name,
    airportIataCode = iata,
    address = "$city, ${if(state.isNotEmpty()) "$state, " else ""}$country"
)

