package com.android.szparag.mvi.navigator

import com.android.szparag.mvi.navigator.NavigationLayer.FOREGROUND
import com.android.szparag.mvi.navigator.NavigationTransitionInAnimation.FADE_IN
import com.android.szparag.mvi.navigator.NavigationTransitionInPolicy.DEFAULT_ADD_TO_STACK
import com.android.szparag.mvi.navigator.NavigationTransitionOutAnimation.FADE_OUT
import com.android.szparag.mvi.navigator.NavigationTransitionOutPolicy.DEFAULT_NONE

typealias LayoutId = Int

abstract class Screen {

  val layer = FOREGROUND

  val transitionInPolicy: NavigationTransitionInPolicy = DEFAULT_ADD_TO_STACK()
  val transitionOutPolicy: NavigationTransitionOutPolicy = DEFAULT_NONE()
  val transitionInAnimation: NavigationTransitionInAnimation = FADE_IN()
  val transitionOutAnimation: NavigationTransitionOutAnimation = FADE_OUT()

  abstract val layoutResource: LayoutId

}