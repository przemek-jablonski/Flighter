package com.android.szparag.flighter.flightsbrowser.views

import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.ArrivalDateSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.ChangeDepartureAirportIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.DepartureDateSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.FlightSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.mvi.views.MviView
import io.reactivex.Observable

interface FlightsBrowserView : MviView<FlightsBrowserViewState> {

  fun departureAirportChangeIntent(): Observable<ChangeDepartureAirportIntent>

  fun departureDateChangeIntent(): Observable<DepartureDateSelectionIntent>

  fun arrivalDateChangeIntent(): Observable<ArrivalDateSelectionIntent>

  fun flightSelectionIntent(): Observable<FlightSelectionIntent>

}