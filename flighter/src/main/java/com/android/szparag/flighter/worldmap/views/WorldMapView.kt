package com.android.szparag.flighter.worldmap.views

import com.android.szparag.flighter.worldmap.states.WorldMapViewState
import io.reactivex.Observable

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 31/03/2018.
 */
interface WorldMapView {

  fun mapInitializedIntent(): Observable<Boolean>

  fun render(state: WorldMapViewState)

}