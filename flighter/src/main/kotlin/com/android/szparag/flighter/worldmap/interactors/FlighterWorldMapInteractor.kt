package com.android.szparag.flighter.worldmap.interactors

import com.android.szparag.flighter.common.interactors.FlighterBaseInteractor
import com.android.szparag.flighter.common.preferences.UserPreferencesRepository
import com.android.szparag.flighter.common.preferences.UserSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlighterWorldMapInteractor @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    userSettingsRepository: UserSettingsRepository
) : FlighterBaseInteractor(userPreferencesRepository, userSettingsRepository), WorldMapInteractor {


}
