package com.android.szparag.flighter.worldmap.states

import com.android.szparag.flighter.common.WorldCoordinates
import com.android.szparag.flighter.worldmap.views.WorldMapView

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 31/03/2018.
 *
 * Available view states for the World Map View.
 * @see WorldMapView
 */

//todo: what about zoom value, should it be passed along with the coords (think so)
sealed class WorldMapViewState {

  /**
   * Onboarding state.
   * Animates (scroll horizontally towards west or east) from given initialCoordinates, with quite generous zoom-out value.
   * @see WorldCoordinates
   *
   * @param initialCoordinates initial coordinates to center the map on and start animating from.
   */
  data class OnboardingViewState(val initialCoordinates: WorldCoordinates) : WorldMapViewState()

  /**
   * Showing Location state.
   * Points at given location, should be at the center of the screen, with quite a bit of zoom-in.
   * @see WorldCoordinates
   *
   * @param coordinates coordinates to center the map on.
   */
  data class ShowingLocationViewState(val coordinates: WorldCoordinates) : WorldMapViewState()

  data class InteractiveViewState(val coordinates: WorldCoordinates) : WorldMapViewState()
  /**
   * Error map state.
   * Communicates that there was an error accessing/instantiating/rendering map.
   */
  //todo: sure this has to be implemented as subclass of THIS sealed class?
  data class ErrorViewState(val throwable: Throwable) : WorldMapViewState()

}