package com.szparag.android.mypermissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.android.szparag.myextensionsbase.emptyMutableList
import com.szparag.android.mypermissions.PermissionCheckEvent.PermissionDeniedEvent
import com.szparag.android.mypermissions.PermissionCheckEvent.PermissionGrantedEvent
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import kotlin.math.absoluteValue

typealias PermissionString = String
typealias PermissionRequestCode = Int
typealias PermissionSubject = PublishSubject<PermissionCheckEvent<AndroidPermission>>
typealias PermissionSubjectWithCode = Pair<PermissionSubject, PermissionRequestCode>

//todo: tests on more permissions
class PermissionManager
/**: PermissionRequestsDelegate*/
{

  //  private val permissionRequests = emptyMutableList<PermissionString>()
  private val permissionResponseSubjects = emptyMutableList<PermissionSubjectWithCode>()


  /**
   * Begin permission requesting procedure. If permission was already granted, immediately throws PermissionGrantedEvent into stream.
   *
   * Part of accesible class API.
   *
   */
  fun askForPermission(sourceActivity: Activity, permission: AndroidPermission): Single<PermissionCheckEvent<AndroidPermission>> {
    PermissionsLogger.log("askForPermission, permission: $permission")
    return if (!isPermissionAlreadyGranted(sourceActivity, permission))
      requestPermission(sourceActivity, permission).singleOrError()
    else
      Single.just(PermissionGrantedEvent(permission))
  }

  /**
   * Check whether given permission is granted or not (if it is already granted, system shouldn't waste resources asking for it).
   * @param permission permission for which query should be performed
   *
   * @return true if permission has already been granted, false if otherwise
   */
  fun isPermissionAlreadyGranted(context: Context, permission: AndroidPermission) =
      ContextCompat.checkSelfPermission(context, permission.androidPermissionString) == PackageManager.PERMISSION_GRANTED

  fun arePermissionsAlreadyGranted(context: Context, permissions: Iterable<AndroidPermission>) =
      !permissions.any { permission -> !isPermissionAlreadyGranted(context, permission) }

  /**
   * Requests given permission.
   *
   * @return subject on which response event for given permission will be broadcasted on.
   * @see PermissionCheckEvent
   * @see PermissionSubject
   */
  private fun requestPermission(sourceActivity: Activity, permission: AndroidPermission): PermissionSubject {
    PermissionsLogger.log("requestPermission, sourceActivity: $sourceActivity, permission: $permission")
    val requestCode: PermissionRequestCode = parsePermissionStringToRequestCode(permission.androidPermissionString)
    val subject: PermissionSubject = PublishSubject.create()
    permissionResponseSubjects.add(Pair(subject, requestCode))
    requestPermissionInternal(sourceActivity, arrayOf(permission.androidPermissionString), requestCode)
    return subject
  }

  /**
   * Rerouting to SDK for permission request
   */
  private fun requestPermissionInternal(sourceActivity: Activity, permissionStrings: Array<out String>,
      requestCode: PermissionRequestCode) {
    PermissionsLogger.log(
        "requestPermissionInternal, sourceActivity: $sourceActivity, permissionStrings: $permissionStrings, requestCode: $requestCode")
    ActivityCompat.requestPermissions(sourceActivity, permissionStrings, requestCode)
  }

  fun onRequestPermissionsResult(requestCode: Int, permissionStrings: Array<out PermissionString>, grantResults: IntArray) {
    PermissionsLogger.log(
        "onRequestPermissionsResult, requestCode: $requestCode, permissionStrings: $permissionStrings, grantResults: $grantResults")
    (0 until permissionStrings.size)
        .forEach { index -> onRequestPermissionResult(requestCode, permissionStrings[index], grantResults[index]) }
  }

  private fun onRequestPermissionResult(requestCode: Int, permissionString: PermissionString, grantResult: Int) {
    PermissionsLogger.log(
        "onRequestPermissionResult, requestCode: $requestCode, permissionString: $permissionString, grantResult: $grantResult")

    //todo: this is probably not nessesary in prod code
    if (requestCode != parsePermissionStringToRequestCode(permissionString)) {
      PermissionsLogger.logError("onRequestPermissionResult, requestCode ($requestCode) doesn't match permissionString ($permissionString)")
    }

    permissionResponseSubjects
        .find { (_, permissionRequestCode) -> permissionRequestCode == requestCode }
        ?.let { (internalSubject, _) ->
          internalSubject.onNext(
              when (grantResult) {
                PackageManager.PERMISSION_GRANTED -> PermissionGrantedEvent(AndroidPermission.getPermissionClassByString(permissionString))
                PackageManager.PERMISSION_DENIED -> PermissionDeniedEvent(AndroidPermission.getPermissionClassByString(permissionString))
                else -> throw UnsupportedOperationException("System has sent other permission response than granted/denied")
              }
          )
          internalSubject.onComplete()
        }

    permissionResponseSubjects.removeAll { (_, permissionRequestCode) ->
      permissionRequestCode == requestCode
    }
  }

  private fun parsePermissionStringToRequestCode(permissionString: PermissionString) =
      convertToLower16bits(permissionString.hashCode().absoluteValue.toLong())

  fun convertToLower16bits(source: Long) = (source and 0xFFFFL shl 16).ushr(16).toInt()


}
