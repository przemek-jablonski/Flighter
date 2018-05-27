package com.android.szparag.columbus

interface ColumbusNavigableScreen {
  var navigationDelegate: Navigator
  fun getScreen(): Screen
}