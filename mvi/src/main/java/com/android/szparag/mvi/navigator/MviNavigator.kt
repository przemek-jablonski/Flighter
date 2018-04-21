package com.android.szparag.mvi.navigator

interface MviNavigator {

  fun showScreen(screen: Screen)

  fun handleBackPress()

}