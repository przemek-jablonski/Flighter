package com.android.szparag.flighter.selectdeparture.interactors

import com.android.szparag.flighter.common.FirebaseQueryModel.FirebaseQuerySuccessful
import com.android.szparag.flighter.common.WorldCoordinates
import com.android.szparag.flighter.common.asObservable
import com.android.szparag.flighter.common.asSingle
import com.android.szparag.flighter.selectdeparture.models.AirportDTO
import com.android.szparag.flighter.selectdeparture.models.AirportModel
import com.android.szparag.flighter.selectdeparture.models.mapToModel
import com.google.firebase.database.DatabaseReference
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlighterSelectDepartureInteractor @Inject constructor(
    private val firebaseReference: DatabaseReference
) : SelectDepartureInteractor {

  init {
    Timber.d("init")
  }

  override fun getAirportsByCity(input: String): Observable<List<AirportModel>> {
    Timber.w("getAirportsByCity, input: $input")

    return firebaseReference.orderByChild("city").equalTo(input).limitToFirst(10).asObservable()
        .filter { it is FirebaseQuerySuccessful }
        .filter { (it as FirebaseQuerySuccessful).snapshot!!.childrenCount == 0L }
        .flatMap{ firebaseReference.orderByChild("city").startAt(input).limitToFirst(10).asObservable() }
        .map { query ->
          query as FirebaseQuerySuccessful
          query.snapshot!!.children.map { snapshotChild -> snapshotChild.getValue(AirportDTO::class.java)!!.mapToModel() } //todo: !!s
        }
  }

  override fun getAirportsByGpsCoordinates(worldCoordinates: WorldCoordinates): Observable<List<AirportModel>> {
    return Observable.just(null)
  }

}