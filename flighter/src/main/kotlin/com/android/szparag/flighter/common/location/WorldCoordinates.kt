package com.android.szparag.flighter.common.location

import com.android.szparag.myextensionsbase.invalidFloatValue

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 31/03/2018.
 */
typealias Meters = Float

data class WorldCoordinates(val latitude: Double, val longitude: Double, val accuracy: Meters? = invalidFloatValue())