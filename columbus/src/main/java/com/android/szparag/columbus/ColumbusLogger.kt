package com.android.szparag.columbus

import android.util.Log

class ColumbusLogger {
  companion object {
    private val LOGGER_TAG_NAVIGATOR = "ColumbusNavigator"
    private val LOGGER_TAG_STACK = "ColumbusNavigationStack"
    @JvmStatic fun navigatorLog(message: String) {
      Log.v(LOGGER_TAG_NAVIGATOR, message)
    }

    @JvmStatic fun stackLog(message: String) {

    }
  }
}