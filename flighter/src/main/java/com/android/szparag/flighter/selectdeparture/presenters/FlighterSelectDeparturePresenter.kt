package com.android.szparag.flighter.selectdeparture.presenters

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterSelectDeparturePresenter @Inject constructor() : SelectDeparturePresenter {

  init {
    Timber.d("init")
  }
}