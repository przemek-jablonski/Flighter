package com.android.szparag.flighter.common.interactors

import com.android.szparag.flighter.common.preferences.UserPreferencesRepository
import com.android.szparag.flighter.common.preferences.UserSettingsRepository
import com.android.szparag.mvi.interactors.MviInteractor

abstract class FlighterBaseInteractor(
    protected val userPreferencesRepository: UserPreferencesRepository,
    protected val userSettingsRepository: UserSettingsRepository
) : MviInteractor