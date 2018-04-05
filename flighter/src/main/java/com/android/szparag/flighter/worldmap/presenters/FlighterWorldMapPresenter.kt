package com.android.szparag.flighter.worldmap.presenters

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterWorldMapPresenter @Inject constructor() : WorldMapPresenter {

  init {
    Timber.d("init")
  }

  override fun test() {
    Timber.d("test")
  }

}