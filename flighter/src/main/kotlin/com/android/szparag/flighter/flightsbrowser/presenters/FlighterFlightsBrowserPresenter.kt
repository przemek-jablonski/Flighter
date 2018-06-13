package com.android.szparag.flighter.flightsbrowser.presenters

import com.android.szparag.flighter.flightsbrowser.interactors.FlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.ArrivalDateSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.DepartureDateSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState.FlightDetailsViewState
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState.FlightsListingViewState
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState.SearchNotStartedViewState
import com.android.szparag.flighter.flightsbrowser.views.FlightsBrowserView
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.AirportSelectedViewState
import com.android.szparag.mvi.models.ModelDistributor
import com.android.szparag.mvi.models.ModelRepository
import com.android.szparag.mvi.presenters.BaseMviPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class FlighterFlightsBrowserPresenter @Inject constructor(
    override val interactor: FlightsBrowserInteractor,
    override val modelRepository: ModelRepository<FlightsBrowserViewState>,
    private val selectDepartureModelDistributor: ModelDistributor<SelectDepartureViewState>
) : BaseMviPresenter<FlightsBrowserView, FlightsBrowserViewState>(), FlightsBrowserPresenter {

  override fun distributeFirstViewState() = null

  override fun processUserIntents(view: FlightsBrowserView) {
    view.arrivalDateChangeIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .zipWith(modelRepository.getModels(),
            BiFunction { arrivalDateIntent: ArrivalDateSelectionIntent, viewState: FlightsBrowserViewState ->
              when (viewState) {
                is FlightDetailsViewState ->
                  FlightDetailsViewState(viewState.airportName, viewState.departureDate, arrivalDateIntent.date,
                      viewState.flightDetailsModel)
                is SearchNotStartedViewState ->
                  SearchNotStartedViewState(viewState.airportName, viewState.departureDate, arrivalDateIntent.date)
                is FlightsListingViewState ->
                  FlightsListingViewState(viewState.airportName, viewState.departureDate, arrivalDateIntent.date, viewState.flights)
              }
            })
        .registerProcessing(this)

    view.departureDateChangeIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .zipWith(modelRepository.getModels(),
            BiFunction { departureDateIntent: DepartureDateSelectionIntent, viewState: FlightsBrowserViewState ->
              when (viewState) {
                is FlightDetailsViewState ->
                  FlightDetailsViewState(viewState.airportName, departureDateIntent.date, viewState.arrivalDate,
                      viewState.flightDetailsModel)
                is SearchNotStartedViewState ->
                  SearchNotStartedViewState(viewState.airportName, departureDateIntent.date, viewState.arrivalDate)
                is FlightsListingViewState ->
                  FlightsListingViewState(viewState.airportName, departureDateIntent.date, viewState.arrivalDate, viewState.flights)
              }
            })
        .registerProcessing(this)

    modelRepository.getModels()
        .subscribeOn(AndroidSchedulers.mainThread())
        .filter { viewState ->
          when (viewState) {
            is SearchNotStartedViewState -> validateSearchQueryParametersInputCompleted(viewState)
            is FlightsListingViewState -> false
            is FlightDetailsViewState -> false
          }
        }

//    view.flightSelectionIntent()
//        .subscribeOn(AndroidSchedulers.mainThread())
//        .

//    view.departureAirportChangeIntent()
//        .subscribeOn(AndroidSchedulers.mainThread())
//        .map { FlightsBrowserViewState. }

  }

  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    modelRepository.replaceModel(
        SearchNotStartedViewState(
            airportName = (selectDepartureModelDistributor.getLatestModel() as AirportSelectedViewState).airportName,
            departureDate = System.currentTimeMillis() / 1000L,
            arrivalDate = null
        )
    )
  }

  override fun onViewAttached(view: FlightsBrowserView) {
    super.onViewAttached(view)
  }

  private fun validateSearchQueryParametersInputCompleted(viewState: SearchNotStartedViewState) =
      viewState.arrivalDate != null && viewState.departureDate != null

}