package com.android.szparag.flighter.selectdeparture.interactors

import com.android.szparag.flighter.common.interactors.FirebaseDatabaseReference
import com.android.szparag.flighter.common.interactors.FirebaseQuery
import com.android.szparag.flighter.common.interactors.GenericFirebaseRepository
import com.android.szparag.flighter.common.interactors.getChildren
import com.android.szparag.flighter.selectdeparture.models.local.AirportModel
import com.android.szparag.flighter.selectdeparture.models.mapToModel
import com.android.szparag.flighter.selectdeparture.models.remote.AirportDTO
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


const val FIREBASE_QUERY_CHILD_CITY = "city"
const val FIREBASE_QUERY_CHILD_LATITUDE = "lat"
const val FIREBASE_QUERY_CHILD_LONGITUDE = "lon"
const val QUERY_GPS_COORDINATES_SIDE_BOUND = 0.40
const val QUERY_GPS_COORDINATES_SIDE_BOUND_EXTENDED = QUERY_GPS_COORDINATES_SIDE_BOUND * 3

@Singleton
class FirebaseAirportsRepository @Inject constructor(
    firebaseReference: FirebaseDatabaseReference
) : GenericFirebaseRepository(firebaseReference), AirportsRepository {

  //todo: firebase returns only FirebaseQuerySuccessful? why? what if it fails?
  override fun getAirportsByCityNameEquality(queryInput: String) =
      constructQueryAirportCityNameEquality(queryInput).asMappedObservable()

  override fun getAirportsByCityNameStartsWith(queryInput: String) =
      constructQueryAirportCityNameStartsWith(queryInput).asMappedObservable()

  override fun getAirportsByLatitude(userLatitude: Double) =
      constructQueryAirportLatitude(userLatitude).asMappedObservable()

  override fun getAirportsByLongitude(userLongitude: Double) =
      constructQueryAirportLongitude(userLongitude).asMappedObservable()

  override fun getAirportsByLatitudeExtended(userLatitude: Double) =
      constructQueryAirportLatitudeExtended(userLatitude).asMappedObservable()

  override fun getAirportsByLongitudeExtended(userLongitude: Double) =
      constructQueryAirportLongitudeExtended(userLongitude).asMappedObservable()

  private fun constructQueryAirportCityNameEquality(queryInput: String) =
      firebaseReference
          .orderByChild(FIREBASE_QUERY_CHILD_CITY)
          .equalTo(queryInput)
          .limitToFirst(3)

  private fun constructQueryAirportCityNameStartsWith(queryInput: String) =
      firebaseReference
          .orderByChild(FIREBASE_QUERY_CHILD_CITY)
          .startAt(queryInput)
          .limitToFirst(10)

  private fun constructQueryAirportLatitude(userLatitude: Double) =
      firebaseReference
          .orderByChild(FIREBASE_QUERY_CHILD_LATITUDE)
          .startAt(userLatitude - QUERY_GPS_COORDINATES_SIDE_BOUND)
          .endAt(userLatitude + QUERY_GPS_COORDINATES_SIDE_BOUND)

  private fun constructQueryAirportLongitude(userLongitude: Double) =
      firebaseReference
          .orderByChild(FIREBASE_QUERY_CHILD_LONGITUDE)
          .startAt(userLongitude - QUERY_GPS_COORDINATES_SIDE_BOUND)
          .endAt(userLongitude + QUERY_GPS_COORDINATES_SIDE_BOUND)

  private fun constructQueryAirportLatitudeExtended(userLatitude: Double) =
      firebaseReference
          .orderByChild(FIREBASE_QUERY_CHILD_LATITUDE)
          .startAt(userLatitude - QUERY_GPS_COORDINATES_SIDE_BOUND)
          .endAt(userLatitude + QUERY_GPS_COORDINATES_SIDE_BOUND)

  private fun constructQueryAirportLongitudeExtended(userLongitude: Double) =
      firebaseReference
          .orderByChild(FIREBASE_QUERY_CHILD_LONGITUDE)
          .startAt(userLongitude - QUERY_GPS_COORDINATES_SIDE_BOUND_EXTENDED)
          .endAt(userLongitude + QUERY_GPS_COORDINATES_SIDE_BOUND_EXTENDED)


  private fun FirebaseQuery.asMappedObservable(): Observable<List<AirportModel>> =
      asObservable()
          .map { query ->
            query
                .getChildren()
                .map { childSnapshot -> childSnapshot.getValue(AirportDTO::class.java)?.mapToModel() ?: throw RuntimeException() }
          }

}