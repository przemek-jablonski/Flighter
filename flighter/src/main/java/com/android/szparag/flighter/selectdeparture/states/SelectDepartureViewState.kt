package com.android.szparag.flighter.selectdeparture.states

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
sealed class SelectDepartureViewState {

  //todo: should have AirportModel as a val
  //todo: there will be gps button
  class SearchNotStartedViewState() : SelectDepartureViewState()

  //todo: rest of them

}