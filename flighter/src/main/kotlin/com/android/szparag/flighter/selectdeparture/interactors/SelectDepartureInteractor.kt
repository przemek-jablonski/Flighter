package com.android.szparag.flighter.selectdeparture.interactors

import com.android.szparag.flighter.common.WorldCoordinates
import com.android.szparag.flighter.selectdeparture.models.AirportModel
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.mvi.models.MviInteractor
import io.reactivex.Completable
import io.reactivex.Single

interface SelectDepartureInteractor: MviInteractor<SelectDepartureViewState> {

  fun getAirportsByCity(input: String): Single<List<AirportModel>>

  fun getAirportsByGpsCoordinates(worldCoordinates: WorldCoordinates): Single<List<AirportModel>>

}