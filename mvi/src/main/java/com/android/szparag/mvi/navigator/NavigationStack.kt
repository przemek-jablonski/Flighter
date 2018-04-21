package com.android.szparag.mvi.navigator

import java.util.Stack

class NavigationStack<T : Screen>(
    private val onScreenPushed: (T) -> (Unit),
    private val onScreenPopped: (T) -> (Unit))
  : Stack<T>() {

  override fun push(item: T) = super.push(item).also(onScreenPushed)

  override fun pop() = super.pop().also(onScreenPopped)

}