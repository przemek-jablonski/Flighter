package com.android.szparag.flighter.worldmap.views

import com.android.szparag.flighter.worldmap.states.WorldMapViewState
import com.android.szparag.mvi.views.MviView
import io.reactivex.Observable

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 31/03/2018.
 */
interface WorldMapView : MviView<WorldMapViewState> {

  fun mapInitializedIntent(): Observable<Boolean>

  override fun render(state: WorldMapViewState)

}