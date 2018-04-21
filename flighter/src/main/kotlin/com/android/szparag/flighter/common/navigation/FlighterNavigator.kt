package com.android.szparag.flighter.common.navigation

import com.android.szparag.mvi.navigator.Navigator
import com.android.szparag.myextensionsbase.emptyMutableList

class FlighterNavigator: Navigator<FlighterScreen> {

  private val navigationStack = emptyMutableList<FlighterScreen>()

  override fun showScreen(screen: FlighterScreen) {

  }

  override fun handleBackPress() {

  }

}