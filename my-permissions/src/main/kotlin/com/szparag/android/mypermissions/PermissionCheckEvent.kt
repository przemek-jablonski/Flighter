package com.szparag.android.mypermissions

sealed class PermissionCheckEvent<out A: AndroidPermission>(val permission: A) {

  class PermissionGrantedEvent<out A: AndroidPermission>(permission: A) : PermissionCheckEvent<A>(permission)

  class PermissionDeniedEvent<out A: AndroidPermission>(permission: A) : PermissionCheckEvent<A>(permission)

}