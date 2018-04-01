package com.android.szparag.flighter.common.util

import com.android.szparag.flighter.common.FlighterApplication
import com.android.szparag.flighter.common.FlighterComponent

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
class Injector {

  companion object {
    fun get(): FlighterComponent = FlighterApplication.component
  }
}