package com.android.szparag.columbus

import android.util.Log

internal class ColumbusLogger {
  companion object {
    private val LOGGER_TAG_NAVIGATOR = "ColumbusNavigator"
    private val LOGGER_TAG_STACK = "ColumbusNavigationStack"
    fun log(message: String) {
      Log.v(LOGGER_TAG_NAVIGATOR, message)
    }

  }
}