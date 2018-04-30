package com.android.szparag.mvi.navigator

import timber.log.Timber
import java.util.*
import kotlin.math.max

typealias OnScreenPushedListener = (Screen) -> (Unit)
typealias OnScreenPoppedListener = (Screen?) -> (Unit)

/**
 * Stack of screens wrapper.
 */
internal class NavigationStack : Stack<Screen>() {

  internal var onScreenPushedListener: OnScreenPushedListener? = null
  internal var onScreenPoppedListener: OnScreenPoppedListener? = null

  /**
   * Standard stack push implementation.
   * Invokes onScreenPushedListener if not null
   */
  override fun push(item: Screen): Screen = push(item, true)

  private fun push(item: Screen, shouldNotifyListener: Boolean = true) =
      super.push(item).also {
        if (shouldNotifyListener) onScreenPushedListener?.invoke(it)
        logChanges()
      }

  /**
   * Prevents foregroundStack from throwing an Exception when there are no items to pop.
   * Invokes onScreenPoppedListener if not null.
   */
  override fun pop(): Screen? = pop(true)

  private fun pop(shouldNotifyListener: Boolean = true) =
      (if (size == 0) null else super.pop()).also {
        if (shouldNotifyListener) onScreenPoppedListener?.invoke(it)
        logChanges()
      }

  fun peekCurrent() = if (size >= 1) get(size - 1) else null

  fun peekPrevious() = if (size >= 2) get(size - 2) else null

  fun removeLastItems(count: Int) {
    (0 until count).forEach { pop(false) }
  }

  fun removeAllItems() = removeLastItems(size)

  private fun logChanges() {
    var log = "\n${this::class.java}(${hashCode()})\n"
    (0 until size).forEach { log += "[$it]: ${get(it)}\n" }
    Timber.d(log)
  }


}