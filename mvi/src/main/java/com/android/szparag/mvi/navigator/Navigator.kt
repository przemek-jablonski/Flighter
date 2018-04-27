package com.android.szparag.mvi.navigator

interface Navigator {

  fun handleBackPress()
  fun goToScreen(screen: Screen)

}