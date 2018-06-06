package com.android.szparag.flighter.flightsbrowser.states

import com.android.szparag.flighter.flightsbrowser.models.local.FlightDetailsModel
import com.android.szparag.flighter.flightsbrowser.models.local.FlightOverviewModel

sealed class FlightsBrowserViewState {

  data class SearchNotStartedViewState(
      val airportName: String,
      val departureDate: Long?,
      val arrivalDate: Long?
  ) : FlightsBrowserViewState()

  data class FlightsListingViewState(
      val airportName: String,
      val departureDate: Long,
      val arrivalDate: Long,
      val flights: List<FlightOverviewModel>
  ) : FlightsBrowserViewState()

  data class FlightDetailsViewState(
      val flightDetailsModel: FlightDetailsModel
  ) : FlightsBrowserViewState()

}