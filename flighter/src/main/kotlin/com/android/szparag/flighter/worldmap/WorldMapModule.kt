package com.android.szparag.flighter.worldmap

import com.android.szparag.flighter.worldmap.interactors.FlighterWorldMapInteractor
import com.android.szparag.flighter.worldmap.interactors.WorldMapInteractor
import com.android.szparag.flighter.worldmap.presenters.FlighterWorldMapPresenter
import com.android.szparag.flighter.worldmap.presenters.WorldMapPresenter
import com.android.szparag.flighter.worldmap.states.WorldMapViewState
import com.android.szparag.mvi.models.BaseMviModelDistributor
import com.android.szparag.mvi.models.ModelDistributor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Module
class WorldMapModule {

  @Provides
  @Singleton
  fun provideWorldMapViewStateDistributor(): ModelDistributor<WorldMapViewState> = BaseMviModelDistributor()

  @Provides
  fun providePresenter(implementation: FlighterWorldMapPresenter): WorldMapPresenter = implementation

  @Provides
  fun provideInteractor(implementation: FlighterWorldMapInteractor): WorldMapInteractor = implementation
}