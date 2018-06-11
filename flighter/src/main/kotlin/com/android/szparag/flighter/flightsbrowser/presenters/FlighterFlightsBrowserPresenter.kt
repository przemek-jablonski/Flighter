package com.android.szparag.flighter.flightsbrowser.presenters

import com.android.szparag.flighter.flightsbrowser.interactors.FlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState.SearchNotStartedViewState
import com.android.szparag.flighter.flightsbrowser.views.FlightsBrowserView
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.AirportSelectedViewState
import com.android.szparag.mvi.models.ModelDistributor
import com.android.szparag.mvi.presenters.BaseMviPresenter
import timber.log.Timber
import javax.inject.Inject

class FlighterFlightsBrowserPresenter @Inject constructor(
    override val interactor: FlightsBrowserInteractor,
    override val modelDistributor: ModelDistributor<FlightsBrowserViewState>,
    private val selectDepartureModelDistributor: ModelDistributor<SelectDepartureViewState>
) : BaseMviPresenter<FlightsBrowserView, FlightsBrowserViewState>(), FlightsBrowserPresenter {

  init {
    Timber.e("init")
  }

  override fun distributeFirstViewState() = SearchNotStartedViewState("asd", null, null)

  override fun processUserIntents(view: FlightsBrowserView) = Unit

  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    Timber.e("onFirstViewAttached")
    //todo: this is kinda weird, FlightsBrowser has to have information about ViewStates of AirportSelected screen, but whatever
    selectDepartureModelDistributor.getLatestModel()
        ?.takeIf { viewState -> viewState is AirportSelectedViewState }
        ?.let { viewState ->
          viewState as AirportSelectedViewState
          SearchNotStartedViewState(viewState.airportName, null, null)
        }
        ?: Unit
  }

  override fun onViewAttached(view: FlightsBrowserView) {
    super.onViewAttached(view)
    Timber.e("onViewAttached, view: $view")
    val model = selectDepartureModelDistributor.getLatestModel()
  }

}