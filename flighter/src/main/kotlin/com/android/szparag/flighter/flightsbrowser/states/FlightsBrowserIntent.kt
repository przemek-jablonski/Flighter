package com.android.szparag.flighter.flightsbrowser.states

import com.android.szparag.flighter.flightsbrowser.models.local.FlightOverviewModel
import com.android.szparag.myextensionsbase.UnixTimestamp

sealed class FlightsBrowserIntent {

  class ChangeDepartureAirportIntent : FlightsBrowserIntent()

  data class DepartureDateSelectionIntent(val date: UnixTimestamp) : FlightsBrowserIntent()

  data class ArrivalDateSelectionIntent(val date: UnixTimestamp) : FlightsBrowserIntent()

  data class FlightSelectionIntent(val flight: FlightOverviewModel) : FlightsBrowserIntent()

}