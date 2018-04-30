package com.android.szparag.mvi.navigator

import android.widget.FrameLayout

interface GlobalActivity {
  val navigationDelegate: Navigator
  val globalContainer: FrameLayout
  fun instantiateFirstScreens()
}