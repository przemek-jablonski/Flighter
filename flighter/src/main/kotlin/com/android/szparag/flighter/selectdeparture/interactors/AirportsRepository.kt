package com.android.szparag.flighter.selectdeparture.interactors

import com.android.szparag.flighter.selectdeparture.models.local.AirportModel
import io.reactivex.Observable

interface AirportsRepository {

  /**
   * Returns Airports that have exactly the same name as the parameter of this method.
   */
  fun getAirportsByCityNameEquality(queryInput: String): Observable<List<AirportModel>>

  /**
   * Returns Airports which name starts with method parameter.
   */
  fun getAirportsByCityNameStartsWith(queryInput: String): Observable<List<AirportModel>>

  /**
   * Returns Airports which geographical latitude is around that specified in the parameter of this method
   */
  fun getAirportsByLatitude(userLatitude: Double): Observable<List<AirportModel>>

  /**
   * Returns Airports which geographical longitude is around that specified in the parameter of this method
   */
  fun getAirportsByLongitude(userLongitude: Double): Observable<List<AirportModel>>

  /**
   * Returns Airports which geographical latitude is around that specified in the parameter of this method
   * (search space is bigger than this in getAirportsByLatitudeExtended() method)
   * @see getAirportsByLatitudeExtended
   */
  fun getAirportsByLatitudeExtended(userLatitude: Double): Observable<List<AirportModel>>

  /**
   * Returns Airports which geographical longitude is around that specified in the parameter of this method
   * (search space is bigger than this in getAirportsByLongitudeExtended() method)
   * @see getAirportsByLongitudeExtended
   */
  fun getAirportsByLongitudeExtended(userLongitude: Double): Observable<List<AirportModel>>

}