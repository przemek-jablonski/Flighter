package com.android.szparag.mvi.navigator

import timber.log.Timber
import java.util.Stack

typealias OnScreenPushedListener = (Screen) -> (Unit)
typealias OnScreenPoppedListener = (Screen?) -> (Unit)

/**
 * Stack of screens wrapper.
 */
class NavigationStack : Stack<Screen>() {

  internal var onScreenPushedListener: OnScreenPushedListener? = null
  internal var onScreenPoppedListener: OnScreenPoppedListener? = null

  /**
   * Standard stack push implementation.
   * Invokes onScreenPushedListener if not null
   */
  override fun push(item: Screen): Screen =
      super.push(item).also {
        onScreenPushedListener?.invoke(it)
      }

  /**
   * Prevents foregroundStack from throwing an Exception when there are no items to pop.
   * Invokes onScreenPoppedListener if not null.
   */
  override fun pop(): Screen? =
      (if (size == 0) null else super.pop()).also {
        onScreenPoppedListener?.invoke(it)
      }

  fun peekCurrent() = if (size >= 1) get(size - 1) else null

  fun peekPrevious() = if (size >= 2) get(size - 2) else null


}