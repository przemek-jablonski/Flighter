package com.szparag.android.mypermissions

import android.Manifest
import com.android.szparag.myextensionsbase.exhaustive

//todo: this doesnt look nice
sealed class AndroidPermission(val androidPermissionString: String) {

  companion object {
    fun getPermissionClassByString(permissionString: PermissionString):AndroidPermission = when(permissionString) {
      AccessFineLocationPermission.androidPermissionString -> AccessFineLocationPermission
      else -> throw UnsupportedOperationException("Could not find permission with given permissionString (no AndroidPermission subclass match).")
    }
  }

  object AccessFineLocationPermission: AndroidPermission(Manifest.permission.ACCESS_FINE_LOCATION)

}
