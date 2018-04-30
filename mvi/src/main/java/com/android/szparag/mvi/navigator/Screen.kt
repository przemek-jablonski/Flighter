package com.android.szparag.mvi.navigator

import com.android.szparag.mvi.navigator.NavigationLayer.FOREGROUND
import com.android.szparag.mvi.navigator.NavigationTransitionInAnimation.FADE_IN
import com.android.szparag.mvi.navigator.NavigationTransitionOutAnimation.FADE_OUT

typealias LayoutId = Int

data class Screen(
    val viewClass: Class<*>, //todo: this should be Class<T: View> but it induces boilerplate, fix that somehow
    val layoutResource: LayoutId,
    val layer: NavigationLayer = FOREGROUND,
    val transitionInPolicy: NavigationTransitionInPolicy = NavigationTransitionInPolicy.DEFAULT_NONE(),
    val transitionOutPolicy: NavigationTransitionOutPolicy = NavigationTransitionOutPolicy.DEFAULT_NONE(),
    val transitionInAnimation: NavigationTransitionInAnimation = FADE_IN(),
    val transitionOutAnimation: NavigationTransitionOutAnimation = FADE_OUT()
)