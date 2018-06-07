package com.android.szparag.flighter.flightsbrowser.presenters

import com.android.szparag.flighter.flightsbrowser.interactors.FlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.flighter.flightsbrowser.views.FlightsBrowserView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import javax.inject.Inject

class FlighterFlightsBrowserPresenter @Inject constructor(override val interactor: FlightsBrowserInteractor)
  : BaseMviPresenter<FlightsBrowserView, FlightsBrowserInteractor, FlightsBrowserViewState>(), FlightsBrowserPresenter {

}