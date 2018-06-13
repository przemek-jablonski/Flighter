package com.android.szparag.flighter.selectdeparture.interactors

import com.android.szparag.flighter.common.interactors.FlighterBaseInteractor
import com.android.szparag.flighter.common.location.LocationFetchingEvent
import com.android.szparag.flighter.common.location.LocationServicesWrapper
import com.android.szparag.flighter.common.location.WorldCoordinates
import com.android.szparag.flighter.common.preferences.UserPreferencesRepository
import com.android.szparag.flighter.common.preferences.UserSettingsRepository
import com.android.szparag.flighter.selectdeparture.models.local.AirportModel
import com.android.szparag.myextensionsandroid.isInRange
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FlighterSelectDepartureInteractor @Inject constructor(
    private val airportsRepository: AirportsRepository,
    private val locationServicesWrapper: LocationServicesWrapper,
    userPreferencesRepository: UserPreferencesRepository,
    userSettingsRepository: UserSettingsRepository
) : FlighterBaseInteractor(userPreferencesRepository, userSettingsRepository), SelectDepartureInteractor {

  init {
    Timber.d("init")
  }

  override fun getUserGpsCoordinates(): Observable<LocationFetchingEvent> {
    Timber.d("getUserGpsCoordinates")
    return locationServicesWrapper.getLocation()
  }

  override fun getAirportsByCity(input: String): Observable<List<AirportModel>> {
    Timber.d("getAirportsByCity, input: $input")
    return airportsRepository.getAirportsByCityNameEquality(input)
        .flatMap { airports ->
          if (airports.isEmpty()) //todo: this looks lame
            airportsRepository.getAirportsByCityNameStartsWith(input)
          else
            Observable.just(airports)
        }
        .map { airports ->
          airports.filter { airport -> airport.airportIataCode.isNotBlank() }
        }
  }

  override fun getAirportsByGpsCoordinates(worldCoordinates: WorldCoordinates): Observable<List<AirportModel>> {
    Timber.d("getAirportsByGpsCoordinates, worldCoordinates: $worldCoordinates")
    return airportsRepository.getAirportsByLatitude(worldCoordinates.latitude)
        .flatMap { airportsRepository.getAirportsByLongitude(worldCoordinates.longitude) }
        .map { airports ->
          airports
              .filter { airport -> validateAirportCoordinates(worldCoordinates, airport) }
              .filter { airport -> airport.airportIataCode.isNotBlank() }
        }
  }

  private fun validateAirportCoordinates(originalCoordinates: WorldCoordinates, airport: AirportModel) =
      airport.coordinates.latitude.isInRange(
          originalCoordinates.latitude - QUERY_GPS_COORDINATES_SIDE_BOUND,
          originalCoordinates.latitude + QUERY_GPS_COORDINATES_SIDE_BOUND
      ) &&
          airport.coordinates.longitude.isInRange(
              originalCoordinates.longitude - QUERY_GPS_COORDINATES_SIDE_BOUND,
              originalCoordinates.longitude + QUERY_GPS_COORDINATES_SIDE_BOUND
          )

}