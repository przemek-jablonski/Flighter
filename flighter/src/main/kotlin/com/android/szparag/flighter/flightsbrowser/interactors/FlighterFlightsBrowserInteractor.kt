package com.android.szparag.flighter.flightsbrowser.interactors

import com.android.szparag.flighter.common.interactors.FlighterBaseInteractor
import com.android.szparag.flighter.common.preferences.UserPreferencesRepository
import com.android.szparag.flighter.common.preferences.UserSettingsRepository
import timber.log.Timber
import javax.inject.Inject

class FlighterFlightsBrowserInteractor @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    userSettingsRepository: UserSettingsRepository
) : FlighterBaseInteractor(userPreferencesRepository, userSettingsRepository), FlightsBrowserInteractor {

  init {
    Timber.d("init")
    userPreferencesRepository.getItem().subscribe {
      Timber.d("userPreferencesRepository.getItem, item: $it")
    }
  }

}