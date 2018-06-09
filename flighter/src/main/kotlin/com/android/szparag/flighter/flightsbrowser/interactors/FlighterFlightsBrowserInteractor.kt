package com.android.szparag.flighter.flightsbrowser.interactors

import com.android.szparag.flighter.common.preferences.UserPreferencesRepository
import timber.log.Timber
import javax.inject.Inject

class FlighterFlightsBrowserInteractor @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository) : FlightsBrowserInteractor {

  init {
    Timber.d("init")
    userPreferencesRepository.getItem().subscribe {
      Timber.d("userPreferencesRepository.getItem, item: $it")
    }
  }

}