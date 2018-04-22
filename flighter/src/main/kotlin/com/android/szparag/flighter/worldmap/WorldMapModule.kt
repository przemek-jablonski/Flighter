package com.android.szparag.flighter.worldmap

import com.android.szparag.flighter.worldmap.interactors.FlighterWorldMapInteractor
import com.android.szparag.flighter.worldmap.interactors.WorldMapInteractor
import com.android.szparag.flighter.worldmap.presenters.FlighterWorldMapPresenter
import com.android.szparag.flighter.worldmap.presenters.WorldMapPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Module
class WorldMapModule {

  @Provides
  fun providePresenter(implementation: FlighterWorldMapPresenter): WorldMapPresenter = implementation

  @Provides
  fun provideInteractor(implementation: FlighterWorldMapInteractor): WorldMapInteractor = implementation
}