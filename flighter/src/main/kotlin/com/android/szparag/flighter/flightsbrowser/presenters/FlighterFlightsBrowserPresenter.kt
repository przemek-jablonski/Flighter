package com.android.szparag.flighter.flightsbrowser.presenters

import com.android.szparag.flighter.flightsbrowser.interactors.FlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.flighter.flightsbrowser.views.FlightsBrowserView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import timber.log.Timber
import javax.inject.Inject

class FlighterFlightsBrowserPresenter @Inject constructor(override val interactor: FlightsBrowserInteractor)
  : BaseMviPresenter<FlightsBrowserView, FlightsBrowserInteractor, FlightsBrowserViewState>(), FlightsBrowserPresenter {

  init {
  }

  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    Timber.d("onFirstViewAttached")
//    view?.render(SearchNotStartedViewState())
  }
}