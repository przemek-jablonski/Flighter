package com.android.szparag.flighter.selectdeparture.interactors

import com.android.szparag.flighter.common.interactors.FirebaseDatabaseReference
import com.android.szparag.flighter.common.interactors.GenericFirebaseInteractor
import com.android.szparag.flighter.common.location.WorldCoordinates
import com.android.szparag.flighter.selectdeparture.models.remote.AirportDTO
import com.android.szparag.myextensionsandroid.isInRange
import javax.inject.Inject
import javax.inject.Singleton


private const val FIREBASE_QUERY_CHILD_CITY = "city"
private const val FIREBASE_QUERY_CHILD_LATITUDE = "lat"
private const val FIREBASE_QUERY_CHILD_LONGITUDE = "lon"
private const val QUERY_GPS_COORDINATES_SIDE_BOUND = 0.40
private const val QUERY_GPS_COORDINATES_SIDE_BOUND_EXTENDED = QUERY_GPS_COORDINATES_SIDE_BOUND * 3

@Singleton
class SelectDepartureFirebaseInteractor @Inject constructor(
    firebaseReference: FirebaseDatabaseReference
) : GenericFirebaseInteractor(firebaseReference) {

  internal fun getAirportsByCityNameEquality(queryInput: String) =
      constructQueryAirportCityNameEquality(queryInput).asObservable()

  internal fun getAirportsByCityNameStartsWith(queryInput: String) =
      constructQueryAirportCityNameStartsWith(queryInput).asObservable()

  internal fun getAirportsByLatitude(userLatitude: Double) =
      constructQueryAirportLatitude(userLatitude).asObservable()

  internal fun getAirportsByLongitude(userLongitude: Double) =
      constructQueryAirportLongitude(userLongitude).asObservable()

  internal fun getAirportsByLatitudeExtended(userLatitude: Double) =
      constructQueryAirportLatitudeExtended(userLatitude).asObservable()

  internal fun getAirportsByLongitudeExtended(userLongitude: Double) =
      constructQueryAirportLongitudeExtended(userLongitude).asObservable()


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


  internal fun validateAirportCoordinates(originalCoordinates: WorldCoordinates, airportDTO: AirportDTO) =
      airportDTO.lat.isInRange(
          originalCoordinates.latitude - QUERY_GPS_COORDINATES_SIDE_BOUND,
          originalCoordinates.latitude + QUERY_GPS_COORDINATES_SIDE_BOUND
      ) &&
          airportDTO.lon.isInRange(
              originalCoordinates.longitude - QUERY_GPS_COORDINATES_SIDE_BOUND,
              originalCoordinates.longitude + QUERY_GPS_COORDINATES_SIDE_BOUND)

}