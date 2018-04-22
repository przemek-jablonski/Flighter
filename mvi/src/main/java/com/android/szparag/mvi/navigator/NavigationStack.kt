package com.android.szparag.mvi.navigator

import java.util.Stack

/**
 * Stack of screens wrapper.
 */
class NavigationStack : Stack<Screen>() {

  /**
   * Prevents stack from throwing an Exception when there are no items to pop.
   */
  override fun pop(): Screen? = if (size == 0) null else super.pop()

}