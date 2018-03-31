package com.android.szparag.flighter.worldmap

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
  fun providePresenter(presenter: FlighterWorldMapPresenter): WorldMapPresenter = presenter

}