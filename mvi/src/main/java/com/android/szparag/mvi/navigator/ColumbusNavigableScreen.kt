package com.android.szparag.mvi.navigator

interface ColumbusNavigableScreen {
  var navigationDelegate: Navigator
  fun getScreen(): Screen
}