package com.szparag.android.mypermissions

import io.reactivex.Single

typealias PermissionRequestAction = (AndroidPermission) -> (Single<PermissionCheckEvent<AndroidPermission>>)

interface PermissionRequestsDelegate {

  var permissionRequestAction: PermissionRequestAction

}