package com.android.szparag.flighter

import android.app.Application
import com.android.szparag.flighter.worldmap.WorldMapModule
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
class FlighterApplication : Application() {

  companion object {
    @JvmStatic
    lateinit var component: FlighterComponent
      private set
  }

  override fun onCreate() {
    super.onCreate()
    Timber.uprootAll()
    Timber.plant(DebugTree())
    Timber.d("onCreate")
    component = DaggerFlighterComponent.builder().worldMapModule(WorldMapModule()).build()
  }
}