package com.android.szparag.columbus

import com.android.szparag.columbus.NavigationTransitionOutPolicy.PERSISTENT_IN_STACK
import com.android.szparag.myextensionsbase.emptyMutableList
import java.util.Arrays
import java.util.Stack

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

  private fun push(item: Screen, shouldNotifyListener: Boolean = true): Screen =
      super.push(item).also {
        if (shouldNotifyListener) onScreenPushedListener?.invoke(it)
        logChanges()
      }

  /**
   * Prevents foregroundStack from throwing an Exception when there are no items to pop.
   * Invokes onScreenPoppedListener if not null.
   */
  override fun pop() = pop(true)

  private fun pop(shouldNotifyListener: Boolean = true) =
      (if (size == 0) null else super.pop()).also {
        if (shouldNotifyListener) onScreenPoppedListener?.invoke(it)
        logChanges()
      }

  override fun peek() =
      if (size == 0) null else super.peek()

  internal fun peekCurrent() = if (size >= 1) get(size - 1) else null

  internal fun peekPrevious() = if (size >= 2) get(size - 2) else null

  internal fun removeLastItemsPreserveTop(removeCount: Int, preserveCount: Int = 1) {
    val preservedItems = emptyMutableList<Screen>()
    (0 until preserveCount).forEach { peek()?.let { preservedItems.add(pop(false)!!) } }
    removeLastItems(removeCount)
    (preserveCount.dec() downTo 0).forEach { step -> push(preservedItems[step], false) }
  }

  private fun removeLastItems(count: Int) {
    (0 until count).forEach {
      if (peek()?.transitionOutPolicy != PERSISTENT_IN_STACK())
        pop(false)
    }
  }

  internal fun removeAllItemsPreserveTop(preserveCount: Int = 1) =
      removeLastItemsPreserveTop(size - 1, preserveCount)

  private fun logChanges() {
    ColumbusLogger.log("\nSTACK CHANGES:\n" + Arrays.toString(toArray()) + "\n")
  }

}