package com.android.szparag.flighter.flightsbrowser

import com.android.szparag.flighter.flightsbrowser.interactors.FlighterFlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.interactors.FlightsBrowserInteractor
import com.android.szparag.flighter.flightsbrowser.presenters.FlighterFlightsBrowserPresenter
import com.android.szparag.flighter.flightsbrowser.presenters.FlightsBrowserPresenter
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.mvi.models.BaseMviModelDistributor
import com.android.szparag.mvi.models.ModelDistributor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FlightsBrowserModule {

  @Provides
  @Singleton
  fun provideWorldMapViewStateDistributor(): ModelDistributor<FlightsBrowserViewState> = BaseMviModelDistributor()

  @Provides
  fun providePresenter(implementation: FlighterFlightsBrowserPresenter): FlightsBrowserPresenter = implementation

  @Provides
  fun provideInteractor(implementation: FlighterFlightsBrowserInteractor): FlightsBrowserInteractor = implementation

}