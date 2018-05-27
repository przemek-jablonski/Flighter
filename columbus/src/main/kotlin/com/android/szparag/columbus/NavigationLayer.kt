package com.android.szparag.columbus

enum class NavigationLayer {

  /**
   * Layer for temporary dialogs, popups, overlays etc. that cover the display fully or partially
   * and usually await user response.
   * This layer responds to back button presses as a first one.
   */
  DIALOG,

  /**
   * Layer for foreground elements. Typically all 'primary screens' in the app should reside here.
   * This layer primarily responds to back button presses (if no dialogs are shown at the moment).
   */
  FOREGROUND,

  /**
   * Layer for elements that should reside at the bottom of the given screen, partially or completely covered by higher layer elements.
   * This layer does not respond to back button presses, but can be dismissed programmatically.
   */
  BACKGROUND,

  /**
   * Layer for constant / persistent UI items which are not changing in an application lifecycle.
   * Eg. toolbars at the top of the screen, navigation bars at the bottom etc.
   * This layer does not respond to back button presses, and CANNOT be dismissed programmatically.
   */
  PERSISTENT

}