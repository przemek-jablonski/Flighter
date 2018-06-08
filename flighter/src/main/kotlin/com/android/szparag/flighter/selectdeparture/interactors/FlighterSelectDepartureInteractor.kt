package com.android.szparag.flighter.selectdeparture.interactors

import com.android.szparag.flighter.common.interactors.getChildren
import com.android.szparag.flighter.common.location.LocationFetchingEvent
import com.android.szparag.flighter.common.location.LocationServicesWrapper
import com.android.szparag.flighter.common.location.WorldCoordinates
import com.android.szparag.flighter.selectdeparture.models.local.AirportModel
import com.android.szparag.flighter.selectdeparture.models.mapToModel
import com.android.szparag.flighter.selectdeparture.models.remote.AirportDTO
import com.android.szparag.myextensionsandroid.isInRange
import com.google.firebase.database.DatabaseReference
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FlighterSelectDepartureInteractor @Inject constructor(
    private val firebaseInteractor: SelectDepartureFirebaseInteractor,
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
    return firebaseInteractor.getAirportsByCityNameEquality(input)
        .flatMap { query ->
          if (query.snapshot.childrenCount == 0L)
            firebaseInteractor.getAirportsByCityNameStartsWith(input)
          else
            Observable.just(query)
        }
        .map { query ->
          query
              .getChildren()
              .map { snapshotChild -> snapshotChild.getValue(AirportDTO::class.java)!! }
              .filter { airportDto -> airportDto.iata.isNotBlank() }

        }
        .map { airportDtosList -> airportDtosList.map { airportDto -> airportDto.mapToModel() } } //todo: in one map
  }

  override fun getAirportsByGpsCoordinates(worldCoordinates: WorldCoordinates): Observable<List<AirportModel>> {
    Timber.d("getAirportsByGpsCoordinates, worldCoordinates: $worldCoordinates")
    return firebaseInteractor.getAirportsByLatitude(worldCoordinates.latitude)
        .flatMap { firebaseInteractor.getAirportsByLongitude(worldCoordinates.longitude) }
        .map { query ->
          query
              .getChildren()
              .map { snapshotChild -> snapshotChild.getValue(AirportDTO::class.java)!! } //todo: !!s
              .filter { airportDto -> firebaseInteractor.validateAirportCoordinates(worldCoordinates, airportDto) }
              .filter { airportDto -> airportDto.iata.isNotBlank() }
        }
        .map { airportDtosList -> airportDtosList.map { airportDto -> airportDto.mapToModel() } } //todo: in one map
  }

}