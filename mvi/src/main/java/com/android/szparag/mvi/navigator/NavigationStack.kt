package com.android.szparag.mvi.navigator

import timber.log.Timber
import java.util.Stack

class NavigationStack: Stack<Screen>() {

//  override fun toString(): String {
//    return "${forEachIndexed { index, screen ->
//      "[$index]: layoutResource: ${screen.layoutResource}, transitionInAnimation: ${screen.transitionInAnimation::class.java.simpleName}"
//    }}"
//  }

}