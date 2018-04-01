package com.android.szparag.flighter

import com.android.szparag.flighter.worldmap.WorldMapModule
import com.android.szparag.flighter.worldmap.views.FlighterWorldMapView
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Component(modules = [(WorldMapModule::class)])
@Singleton
interface FlighterComponent {

  fun inject(target: FlighterWorldMapView)

}