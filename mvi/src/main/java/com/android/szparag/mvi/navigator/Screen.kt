package com.android.szparag.mvi.navigator

import com.android.szparag.mvi.navigator.NavigationTransitionInAnimation.FADE_IN
import com.android.szparag.mvi.navigator.NavigationTransitionInPolicy.DEFAULT_ADD_TO_STACK
import com.android.szparag.mvi.navigator.NavigationTransitionOutAnimation.FADE_OUT
import com.android.szparag.mvi.navigator.NavigationTransitionOutPolicy.DEFAULT_NONE

typealias LayoutId = Int

data class Screen(
    val viewClass: Class<*>, //todo: this should be Class<T: View> but it induces boilerplate, fix that somehow
    val layoutResource: LayoutId,
    val transitionInPolicy: NavigationTransitionInPolicy = DEFAULT_ADD_TO_STACK(),
    val transitionOutPolicy: NavigationTransitionOutPolicy = DEFAULT_NONE(),
    val transitionInAnimation: NavigationTransitionInAnimation = FADE_IN(),
    val transitionOutAnimation: NavigationTransitionOutAnimation = FADE_OUT()
)

{
  override fun toString() =
      "Screen(viewClass: ${viewClass.simpleName}, layoutResource: $layoutResource, transitionInPolicy: ${transitionInPolicy::class.java.simpleName}, transitionOutPolicy: ${transitionOutPolicy::class.java.simpleName}, transitionInAnimation: ${transitionInAnimation::class.java.simpleName}, transitionOutAnimation: ${transitionOutAnimation::class.java.simpleName}"
}