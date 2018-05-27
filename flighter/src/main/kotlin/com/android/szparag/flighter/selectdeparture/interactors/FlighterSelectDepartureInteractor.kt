package com.android.szparag.flighter.selectdeparture.interactors

import com.android.szparag.flighter.common.asObservable
import com.android.szparag.flighter.common.getChildren
import com.android.szparag.flighter.common.location.LocationFetchingEvent
import com.android.szparag.flighter.common.location.LocationServicesWrapper
import com.android.szparag.flighter.common.location.WorldCoordinates
import com.android.szparag.flighter.selectdeparture.models.AirportDTO
import com.android.szparag.flighter.selectdeparture.models.AirportModel
import com.android.szparag.flighter.selectdeparture.models.mapToModel
import com.android.szparag.myextensionsandroid.isInRange
import com.google.firebase.database.DatabaseReference
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private const val FIREBASE_QUERY_CHILD_CITY = "city"
private const val FIREBASE_QUERY_CHILD_LATITUDE = "lat"
private const val FIREBASE_QUERY_CHILD_LONGITUDE = "lon"
private const val QUERY_GPS_COORDINATES_SIDE_BOUND = 1.50
private const val QUERY_GPS_COORDINATES_SIDE_BOUND_EXTENDED = QUERY_GPS_COORDINATES_SIDE_BOUND * 2

@Singleton
class FlighterSelectDepartureInteractor @Inject constructor(
    private val firebaseReference: DatabaseReference,
    private val locationServicesWrapper: LocationServicesWrapper
) : SelectDepartureInteractor {


  init {
    Timber.d("init")
  }

  override fun getUserGpsCoordinates(): Observable<LocationFetchingEvent> {
    Timber.d("getUserGpsCoordinates")
    return locationServicesWrapper.getLocation()
  }

  override fun getAirportsByCity(input: String): Observable<List<AirportModel>> {
    Timber.d("getAirportsByCity, input: $input")
    return constructQueryAirportCityNameEquality(input).asObservable()
        .flatMap { query ->
          if (query.snapshot.childrenCount == 0L)
            constructQueryAirportCityNameStartsWith(input).asObservable()
          else
            Observable.just(query)
        }
        .map { query ->
          query.getChildren().map { snapshotChild ->
            snapshotChild.getValue(AirportDTO::class.java)!!.mapToModel()//todo: !!s
          }
        }
  }

  override fun getAirportsByGpsCoordinates(worldCoordinates: WorldCoordinates): Observable<List<AirportModel>> {
    Timber.d("getAirportsByGpsCoordinates, worldCoordinates: $worldCoordinates")
    return constructQueryAirportLatitude(worldCoordinates.latitude).asObservable()
        .flatMap { constructQueryAirportLongitude(worldCoordinates.longitude).asObservable() }
        .map { query ->
          query
              .getChildren()
              .map { snapshotChild -> snapshotChild.getValue(AirportDTO::class.java)!! } //todo: !!s
              .filter { airportDTO -> validateAirportCoordinates(worldCoordinates, airportDTO) }
        }
        .map { airportDTOs ->
          airportDTOs.map { airportDTO -> airportDTO.mapToModel() }
        }
  }

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

  private fun validateAirportCoordinates(originalCoordinates: WorldCoordinates, airportDTO: AirportDTO) =
      airportDTO.lat.isInRange(
          originalCoordinates.latitude - QUERY_GPS_COORDINATES_SIDE_BOUND,
          originalCoordinates.latitude + QUERY_GPS_COORDINATES_SIDE_BOUND
      ) &&
          airportDTO.lon.isInRange(
              originalCoordinates.longitude - QUERY_GPS_COORDINATES_SIDE_BOUND,
              originalCoordinates.longitude + QUERY_GPS_COORDINATES_SIDE_BOUND)


}