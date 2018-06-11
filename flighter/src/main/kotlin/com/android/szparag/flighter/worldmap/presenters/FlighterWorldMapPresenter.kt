package com.android.szparag.flighter.worldmap.presenters

import com.android.szparag.flighter.common.location.WorldCoordinates
import com.android.szparag.flighter.worldmap.interactors.WorldMapInteractor
import com.android.szparag.flighter.worldmap.states.WorldMapViewState
import com.android.szparag.flighter.worldmap.states.WorldMapViewState.OnboardingViewState
import com.android.szparag.flighter.worldmap.views.WorldMapView
import com.android.szparag.mvi.models.ModelRepository
import com.android.szparag.mvi.presenters.BaseMviPresenter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterWorldMapPresenter @Inject constructor(
    override val interactor: WorldMapInteractor,
    override val modelDistributor: ModelRepository<WorldMapViewState>
) : BaseMviPresenter<WorldMapView, WorldMapViewState>(), WorldMapPresenter {

  override fun distributeFirstViewState() = OnboardingViewState(WorldCoordinates(51.509865, -0.118092))

  override fun processUserIntents(view: WorldMapView) = Unit

}