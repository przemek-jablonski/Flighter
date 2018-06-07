package com.android.szparag.flighter.flightsbrowser.presenters

import com.android.szparag.flighter.flightsbrowser.interactors.FlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.flighter.flightsbrowser.views.FlightsBrowserView
import com.android.szparag.mvi.presenters.MviPresenter

interface FlightsBrowserPresenter : MviPresenter<FlightsBrowserView, FlightsBrowserViewState> {

}