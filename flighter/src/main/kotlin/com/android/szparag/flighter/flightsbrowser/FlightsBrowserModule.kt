package com.android.szparag.flighter.flightsbrowser

import com.android.szparag.flighter.flightsbrowser.interactors.FlighterFlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.interactors.FlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.presenters.FlighterFlightsBrowserPresenter
import com.android.szparag.flighter.flightsbrowser.presenters.FlightsBrowserPresenter
import dagger.Module
import dagger.Provides

@Module
class FlightsBrowserModule {

  @Provides
  fun providePresenter(implementation: FlighterFlightsBrowserPresenter): FlightsBrowserPresenter = implementation

  @Provides
  fun provideInteractor(implementation: FlighterFlightsBrowserInteractor): FlightsBrowserInteractor = implementation

}