package com.android.szparag.flighter.common.permissions

sealed class PermissionCheckEvent(permissionString: String)

class PermissionGrantedEvent(permissionString: String): PermissionCheckEvent(permissionString)

class PermissionDeniedEvent(permissionString: String): PermissionCheckEvent(permissionString)