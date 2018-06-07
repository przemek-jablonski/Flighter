package com.android.szparag.flighter.selectdeparture.presenters

import com.android.szparag.flighter.selectdeparture.interactors.SelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.views.SelectDepartureView
import com.android.szparag.mvi.presenters.MviPresenter

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
interface SelectDeparturePresenter : MviPresenter<SelectDepartureView, SelectDepartureViewState>