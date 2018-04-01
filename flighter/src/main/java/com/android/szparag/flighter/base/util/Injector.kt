package com.android.szparag.flighter.base.util

import com.android.szparag.flighter.base.FlighterApplication
import com.android.szparag.flighter.base.FlighterComponent

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
class Injector {

  companion object {
    fun get(): FlighterComponent = FlighterApplication.component
  }
}