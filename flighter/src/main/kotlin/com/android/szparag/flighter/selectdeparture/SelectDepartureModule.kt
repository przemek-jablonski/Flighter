package com.android.szparag.flighter.selectdeparture

import com.android.szparag.flighter.selectdeparture.interactors.AirportsRepository
import com.android.szparag.flighter.selectdeparture.interactors.FirebaseAirportsRepository
import com.android.szparag.flighter.selectdeparture.interactors.FlighterSelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.interactors.SelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.presenters.FlighterSelectDeparturePresenter
import com.android.szparag.flighter.selectdeparture.presenters.SelectDeparturePresenter
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.mvi.models.BaseMviModelRepository
import com.android.szparag.mvi.models.ModelDistributor
import com.android.szparag.mvi.models.ModelRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Module
class SelectDepartureModule {

  @Provides
  @Singleton
  fun provideWorldMapViewStateRepository(): ModelRepository<SelectDepartureViewState> = BaseMviModelRepository()

  @Provides
  @Singleton
  fun provideWorldMapViewStateDistributor(
      repository: ModelRepository<SelectDepartureViewState>): ModelDistributor<SelectDepartureViewState> = repository


  @Provides
  fun providePresenter(implementation: FlighterSelectDeparturePresenter): SelectDeparturePresenter = implementation

  @Provides
  fun provideInteractor(implementation: FlighterSelectDepartureInteractor): SelectDepartureInteractor = implementation

  @Provides
  fun provideAirportRepository(implementation: FirebaseAirportsRepository): AirportsRepository = implementation

}