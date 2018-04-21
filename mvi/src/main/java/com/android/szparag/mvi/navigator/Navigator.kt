package com.android.szparag.mvi.navigator

interface Navigator<in T: Any> {

  fun showScreen(screen: T)

  fun handleBackPress()

}