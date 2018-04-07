package com.android.szparag.flighter.worldmap.presenters

import com.android.szparag.flighter.worldmap.states.WorldMapViewState
import com.android.szparag.flighter.worldmap.views.WorldMapView
import com.android.szparag.mvi.presenters.MviPresenter

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
interface WorldMapPresenter : MviPresenter<WorldMapView, WorldMapViewState> {

  fun test()

}