package com.android.szparag.flighter.flightsbrowser.presenters

import com.android.szparag.flighter.flightsbrowser.interactors.FlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState.SearchNotStartedViewState
import com.android.szparag.flighter.flightsbrowser.views.FlightsBrowserView
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.AirportSelectedViewState
import com.android.szparag.mvi.models.ModelDistributor
import com.android.szparag.mvi.models.ModelRepository
import com.android.szparag.mvi.presenters.BaseMviPresenter
import javax.inject.Inject

class FlighterFlightsBrowserPresenter @Inject constructor(
    override val interactor: FlightsBrowserInteractor,
    override val modelDistributor: ModelRepository<FlightsBrowserViewState>,
    private val selectDepartureModelDistributor: ModelDistributor<SelectDepartureViewState>
) : BaseMviPresenter<FlightsBrowserView, FlightsBrowserViewState>(), FlightsBrowserPresenter {

  override fun distributeFirstViewState() = null

  override fun processUserIntents(view: FlightsBrowserView) = Unit

  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    //todo: this is kinda weird, FlightsBrowser has to have information about ViewStates of AirportSelected screen, but whatever
    modelDistributor.replaceModel(
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

}