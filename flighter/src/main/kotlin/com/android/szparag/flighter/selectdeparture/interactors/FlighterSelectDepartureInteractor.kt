package com.android.szparag.flighter.selectdeparture.interactors

import com.android.szparag.flighter.common.WorldCoordinates
import com.android.szparag.flighter.selectdeparture.models.AirportDTO
import com.android.szparag.flighter.selectdeparture.models.AirportModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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

  override fun getAirportsByCity(input: String): Single<List<AirportModel>> {
    Timber.w("getAirportsByCity, input: $input")

    firebaseReference.orderByChild("city").equalTo(input).limitToFirst(10).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(error: DatabaseError?) {
        Timber.e("(exact) onCancelled, p0: $error")
      }

      override fun onDataChange(snapshot: DataSnapshot?) {
        Timber.d("(exact) onDataChange, p0: $snapshot")
        if (snapshot!!.children.count() == 0) {

          firebaseReference.orderByChild("city").startAt(input).limitToFirst(
              10).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
              Timber.e("(startAt) onCancelled, error: $error")
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
              Timber.d("(startAt) onDataChange, snapshot: $snapshot")

              snapshot!!.children.forEachIndexed { index, child ->
                Timber.e("(startAt) onDataChange, index: $index, child: ${child.getValue(AirportDTO::class.java)}")
              }

            }
          })


        } else {

          snapshot.children.forEachIndexed { index, child ->
            Timber.e("(exact) onDataChange, index: $index, child: ${child.getValue(AirportDTO::class.java)}")
          }

        }


      }

    })


    return Single.never()
  }

  override fun getAirportsByGpsCoordinates(worldCoordinates: WorldCoordinates): Single<List<AirportModel>> {
    return Single.never()
  }

}