package com.android.szparag.mvi.navigator

enum class NavigationLayer {

  /**
   * Layer for dialogs, popups, overlays etc.
   */
  DIALOGS,

  /**
   * Layer for foreground elements. Typically all 'primary screens' in the app should reside here.
   * This layer primarily responds to back button presses.
   */
  FOREGROUND,

  /**
   * Layer for elements that should reside at the bottom of the given screen, partially or completely covered by higher layer elements.
   */
  BACKGROUND

}