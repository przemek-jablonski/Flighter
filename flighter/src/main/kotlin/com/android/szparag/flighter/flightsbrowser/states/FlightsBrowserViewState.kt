package com.android.szparag.flighter.flightsbrowser.states

import com.android.szparag.flighter.flightsbrowser.models.local.FlightDetailsModel
import com.android.szparag.flighter.flightsbrowser.models.local.FlightOverviewModel

sealed class FlightsBrowserViewState(
    val airportName: String,
    val departureDate: Long?,
    val arrivalDate: Long?
) {

  class SearchNotStartedViewState(airportName: String, departureDate: Long?, arrivalDate: Long?)
    : FlightsBrowserViewState(airportName, departureDate, arrivalDate)

  class FlightsListingViewState(
      airportName: String,
      departureDate: Long?,
      arrivalDate: Long?,
      val flights: List<FlightOverviewModel>)
    : FlightsBrowserViewState(airportName, departureDate, arrivalDate)

  class FlightDetailsViewState(
      airportName: String,
      departureDate: Long?,
      arrivalDate: Long?,
      val flightDetailsModel: FlightDetailsModel)
    : FlightsBrowserViewState(airportName, departureDate, arrivalDate)

}