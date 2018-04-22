@file:Suppress("unused")

package com.android.szparag.mvi.navigator

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BaseInterpolator

typealias Millis = Long

sealed class NavigationTransitionAnimation {
  companion object Constants {
    val DURATION_SHORT: Millis = 250
    val DURATION_NORMAL: Millis = 500
    val DURATION_LONG: Millis = 1000
    val DEGREES_DIRECTION_RIGHT = 180
    val DEGREES_DIRECTION_LEFT = 0
    val DEGREES_DIRECTION_TOP = 90
    val DEGREES_DIRECTION_BOTTOM = 270
  }
}

//todo: scale_in
sealed class NavigationTransitionInAnimation : NavigationTransitionAnimation() {
  class FADE_IN(
      val interpolator: BaseInterpolator = AccelerateDecelerateInterpolator(),
      val duration: Millis = DURATION_NORMAL
  ) : NavigationTransitionInAnimation()

  class MOVE_IN(
      val degrees: Int = DEGREES_DIRECTION_RIGHT,
      val interpolator: BaseInterpolator = AccelerateDecelerateInterpolator(),
      val duration: Millis = DURATION_NORMAL
  ) : NavigationTransitionInAnimation()
}

//todo: scale_out
sealed class NavigationTransitionOutAnimation : NavigationTransitionAnimation() {
  class FADE_OUT(
      val interpolator: BaseInterpolator = AccelerateDecelerateInterpolator(),
      val duration: Millis = DURATION_NORMAL
  ) : NavigationTransitionOutAnimation()

  class MOVE_OUT(
      val degrees: Int = DEGREES_DIRECTION_LEFT,
      val interpolator: BaseInterpolator = AccelerateDecelerateInterpolator(),
      val duration: Millis = DURATION_NORMAL
  ) : NavigationTransitionOutAnimation()

}