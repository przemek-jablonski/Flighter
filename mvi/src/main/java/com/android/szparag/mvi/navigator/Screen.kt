package com.android.szparag.mvi.navigator

import com.android.szparag.mvi.navigator.NavigationTransitionInAnimation.*
import com.android.szparag.mvi.navigator.NavigationTransitionInPolicy.*
import com.android.szparag.mvi.navigator.NavigationTransitionOutAnimation.*
import com.android.szparag.mvi.navigator.NavigationTransitionOutPolicy.*

typealias LayoutId = Int

data class Screen(
    val layoutResource: LayoutId,
    val transitionInPolicy: NavigationTransitionInPolicy = DEFAULT_ADD_TO_STACK(),
    val transitionOutPolicy: NavigationTransitionOutPolicy = DEFAULT_NONE(),
    val transitionInAnimation: NavigationTransitionInAnimation = FADE_IN(),
    val transitionOutAnimation: NavigationTransitionOutAnimation = FADE_OUT()
)