package com.android.szparag.columbus

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.RelativeLayout

interface ColumbusNavigationController {
  val navigationDelegate: Navigator
  val globalContainer: RelativeLayout
  fun instantiateFirstScreens()
}