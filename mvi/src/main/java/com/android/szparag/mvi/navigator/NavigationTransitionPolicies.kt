@file:Suppress("unused")

package com.android.szparag.mvi.navigator

/**
 * Specifies what policy should Navigator apply when screen is appearing on the device screen.
 */
sealed class NavigationTransitionInPolicy {

  /**
   * Default policy - add the screen to the Navigation Stack and don't apply any further modifications to it.
   */
  class DEFAULT_ADD_TO_STACK : NavigationTransitionInPolicy()

  /**
   * In addition to adding the screen to the Navigation Stack, pop specified amount of screens from the foregroundStack.
   */
  class KILL_PREVIOUS(val itemCount: Int = 1) : NavigationTransitionInPolicy()

  /**
   * In addition to adding the screen to the Navigation Stack, pop every item from the Stack.
   * (ie. when the user presses back button the app is dismissed, since there is no screen to navigate 'back' to)
   */
  class KILL_ALL_PREVIOUS : NavigationTransitionInPolicy()

}

/**
 * Specifies what policy should Navigator apply when screen is disappearing from the device screen.
 */
sealed class NavigationTransitionOutPolicy {

  /**
   * Default policy - don't do any modifications to the Navigation Stack.
   */
  class DEFAULT_NONE : NavigationTransitionOutPolicy()

  /**
   * Pop current item from the foregroundStack.
   * May be useful when using Dialog-type screens - when dismissed, it will be removed from the Navigation Stack.
   */
  class KILL_ITSELF : NavigationTransitionOutPolicy()

  /**
   * Pop specified amount of screens from the Navigation Stack.
   */
  class KILL_LAST(val itemCount: Int = 1) : NavigationTransitionOutPolicy()

  /**
   * Pop every item from the foregroundStack, except the current one.
   */
  class KILL_ALL_PREVIOUS : NavigationTransitionOutPolicy()

  /**
   * Pop every item from the foregroundStack, including the current one.
   */
  class KILL_ALL: NavigationTransitionOutPolicy()

  /**
   * Flags this screen as persistent, which means it cannot be 'killed' by KILL_PREVIOUS or KILL_ALL_PREVIOUS policies.
   */
  class PERSISTENT_IN_STACK: NavigationTransitionOutPolicy()

}