package com.android.szparag.flighter.common.permissions

import android.Manifest

//todo: this doesnt look nice
//permisions used in an app
sealed class AndroidPermission(val androidPermissionString: String)

class AccessFineLocationPermission: AndroidPermission(Manifest.permission.ACCESS_FINE_LOCATION)