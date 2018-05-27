package com.android.szparag.flighter.worldmap.presenters

import com.android.szparag.flighter.common.location.WorldCoordinates
import com.android.szparag.flighter.worldmap.interactors.WorldMapInteractor
import com.android.szparag.flighter.worldmap.states.WorldMapViewState
import com.android.szparag.flighter.worldmap.views.WorldMapView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterWorldMapPresenter @Inject constructor(override var interactor: WorldMapInteractor) : BaseMviPresenter<WorldMapView, WorldMapInteractor, WorldMapViewState>(),
  WorldMapPresenter {

  init {
    Timber.d("null")
  }

  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    Timber.d("onFirstViewAttached")
    view?.render(WorldMapViewState.OnboardingViewState(WorldCoordinates(51.509865, -0.118092)))
  }

  //____________________________temporary

  override fun onViewAttached(view: WorldMapView) {
    super.onViewAttached(view)
    Timber.d("onViewAttached, view: $view")
  }

  override fun onViewDetached(view: WorldMapView) {
    super.onViewDetached(view)
    Timber.d("onViewDetached, view: $view")
  }

}