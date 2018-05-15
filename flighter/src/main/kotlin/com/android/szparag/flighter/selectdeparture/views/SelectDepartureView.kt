package com.android.szparag.flighter.selectdeparture.views

import com.android.szparag.flighter.selectdeparture.states.SelectDepartureIntent.GpsSearchIntent
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureIntent.TextSearchIntent
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.mvi.views.MviView
import io.reactivex.Observable

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
interface SelectDepartureView : MviView<SelectDepartureViewState> {

  fun searchWithTextIntent(): Observable<TextSearchIntent>

  fun searchWithGpsIntent(): Observable<GpsSearchIntent>

}