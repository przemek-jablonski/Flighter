package com.android.szparag.flighter.selectdeparture.views

import com.android.szparag.flighter.R.layout
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.mvi.navigator.Screen

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
interface SelectDepartureView {


  fun render(state: SelectDepartureViewState)

}