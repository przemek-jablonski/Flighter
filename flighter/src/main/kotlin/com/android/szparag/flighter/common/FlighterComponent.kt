package com.android.szparag.flighter.common

import com.android.szparag.flighter.login.LoginModule
import com.android.szparag.flighter.login.views.FlighterLoginView
import com.android.szparag.flighter.selectdeparture.SelectDepartureModule
import com.android.szparag.flighter.selectdeparture.views.FlighterSelectDepartureView
import com.android.szparag.flighter.worldmap.WorldMapModule
import com.android.szparag.flighter.worldmap.views.FlighterWorldMapView
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Component(modules = [(WorldMapModule::class), (LoginModule::class), (SelectDepartureModule::class)])
@Singleton
interface FlighterComponent {

  fun inject(target: FlighterWorldMapView)
  fun inject(target: FlighterLoginView)
  fun inject(target: FlighterSelectDepartureView)

}