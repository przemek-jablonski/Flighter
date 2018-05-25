package com.android.szparag.flighter.common.permissions

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.android.szparag.myextensionsbase.emptyMutableList
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.absoluteValue

typealias PermissionString = String
typealias PermissionRequestCode = Int
typealias PermissionSubject = Pair<PublishSubject<PermissionCheckEvent>, PermissionRequestCode>

//todo: tests on more permissions
//todo:
//I really hate the idea of having any sort of MANAGER in any codebase, but it's not my fault that permissions in SDK are written that way lol
@Singleton
class PermissionManager @Inject constructor() {

  //  private val permissionRequests = emptyMutableList<PermissionString>()
  private val permissionResponseSubjects = emptyMutableList<PermissionSubject>()

  fun requestPermissions(sourceActivity: Activity, permissionStrings: Iterable<PermissionString>) {
    Timber.i("requestPermissions, sourceActivity: $sourceActivity, permissionStrings: $permissionStrings")
    permissionStrings.forEach { permissionString -> requestPermission(sourceActivity, permissionString) }
  }

  fun requestPermission(sourceActivity: Activity, permissionString: PermissionString) {
    Timber.i("requestPermission, sourceActivity: $sourceActivity, permissionString: $permissionString")
    val requestCode = parsePermissionStringToRequestCode(permissionString)
    permissionResponseSubjects.add(Pair(PublishSubject.create(), requestCode))
    ActivityCompat.requestPermissions(
        sourceActivity,
        arrayOf(permissionString),
        requestCode
    )
  }

  fun onRequestPermissionsResult(requestCode: Int, permissionStrings: Array<out PermissionString>, grantResults: IntArray) {
    Timber.i("onRequestPermissionsResult, requestCode: $requestCode, permissionStrings: $permissionStrings, grantResults: $grantResults")
    (0 until permissionStrings.size)
        .forEach { index -> onRequestPermissionResult(requestCode, permissionStrings[index], grantResults[index]) }
  }

  private fun onRequestPermissionResult(requestCode: Int, permissionString: PermissionString, grantResult: Int) {
    Timber.i("onRequestPermissionResult, requestCode: $requestCode, permissionString: $permissionString, grantResult: $grantResult")

    //todo: this is probably not nessesary in prod code
    if (requestCode != parsePermissionStringToRequestCode(permissionString)) {
      Timber.e("onRequestPermissionResult, requestCode ($requestCode) doesn't match permissionString ($permissionString)")
    }

    permissionResponseSubjects
        .find { (_, permissionRequestCode) -> permissionRequestCode == requestCode }
        ?.let { (internalSubject, _) ->
          internalSubject.onNext(
              when (grantResult) {
                PackageManager.PERMISSION_GRANTED -> PermissionGrantedEvent(permissionString)
                PackageManager.PERMISSION_DENIED -> PermissionDeniedEvent(permissionString)
                else -> throw UnsupportedOperationException()
              }
          )
          internalSubject.onComplete()
        }

    permissionResponseSubjects.removeAll { (_, permissionRequestCode) ->
      permissionRequestCode == requestCode
    }
  }

  private fun parsePermissionStringToRequestCode(permissionString: PermissionString) =
      permissionString.hashCode().absoluteValue

}
