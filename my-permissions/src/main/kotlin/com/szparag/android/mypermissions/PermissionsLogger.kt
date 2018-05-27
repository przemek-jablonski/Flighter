package com.szparag.android.mypermissions

import android.util.Log

internal class PermissionsLogger {

  companion object {
    private val LOGGER_TAG_PERMISSIONS = "Permissions"
    fun log(message: String) = log(Log.VERBOSE, message)
    fun logError(message: String) = log(Log.ERROR, message)
    private fun log(level: Int, message: String) =Log.println(level, LOGGER_TAG_PERMISSIONS, message)
  }

}