package com.android.szparag.columbus

import com.android.szparag.columbus.NavigationLayer.*

interface Navigator { //todo: delete this interface, it is unnessesary

  fun handleBackPress() //todo: this should not be in this interface
  fun goToScreen(screen: Screen)
  fun goBack(layer: NavigationLayer = FOREGROUND)
  fun closeDialog() = goBack(DIALOG)
  fun onHandleFirstRender(screen: Screen)

}