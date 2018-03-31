package com.android.szparag.flighter

import com.android.szparag.flighter.worldmap.WorldMapModule
import dagger.Component

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Component(modules = [(WorldMapModule::class)])
interface FlighterComponent {

}