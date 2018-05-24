package com.android.szparag.flighter.common

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import com.android.szparag.columbus.ColumbusNavigationRoot
import com.android.szparag.columbus.ColumbusNavigator
import com.android.szparag.columbus.Navigator
import com.android.szparag.flighter.R
import com.android.szparag.flighter.common.util.ActivityLifecycleState
import com.android.szparag.flighter.common.util.ActivityLifecycleState.ONCREATE
import com.android.szparag.flighter.common.util.ActivityLifecycleState.ONDESTROY
import com.android.szparag.flighter.common.util.ActivityLifecycleState.ONLOWMMEMORY
import com.android.szparag.flighter.common.util.ActivityLifecycleState.ONPAUSE
import com.android.szparag.flighter.common.util.ActivityLifecycleState.ONRESUME
import com.android.szparag.flighter.common.util.ActivityLifecycleState.ONSAVEINSTANCESTATE
import com.android.szparag.flighter.common.util.ActivityLifecycleState.ONSTART
import com.android.szparag.flighter.common.util.ActivityLifecycleState.ONSTOP
import com.android.szparag.flighter.common.util.Injector
import com.android.szparag.flighter.login.views.FlighterLoginView
import com.android.szparag.flighter.worldmap.views.FlighterWorldMapView
import com.android.szparag.kotterknife.bindView
import io.reactivex.subjects.Subject
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.absoluteValue

class FlighterGlobalActivity : AppCompatActivity(), ColumbusNavigationRoot {

  //todo: reset buffer after onResume or onStop or onBundle callbacks are called
  @Inject
  lateinit var activityStateSubject: Subject<ActivityLifecycleState>

  override val globalContainer: RelativeLayout by bindView(R.id.globalContainer)
  override val navigationDelegate: Navigator by lazy {
    ColumbusNavigator(
        globalContainer = globalContainer,
        inflater = layoutInflater,
        closeAppRequestResponse = this::finish
    )
  }
  //todo: daggerize dat
  //todo: builder

  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.d("onCreate, savedInstanceState: $savedInstanceState")
    super.onCreate(savedInstanceState)
    Injector.get().inject(this)
    activityStateSubject.onNext(ONCREATE)
    setContentView(R.layout.container_global_flighter)
    instantiateFirstScreens()
  }

  override fun instantiateFirstScreens() {
    navigationDelegate.goToScreen(FlighterLoginView.screenData)
    navigationDelegate.goToScreen(FlighterWorldMapView.screenData)
  }

  override fun onStart() {
    Timber.d("onStart")
    super.onStart()
    activityStateSubject.onNext(ONSTART)
  }

  override fun onResume() {
    Timber.d("onResume")
    super.onResume()
    activityStateSubject.onNext(ONRESUME)
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    Timber.d("onSaveInstanceState, outState: $outState")
    super.onSaveInstanceState(outState)
    activityStateSubject.onNext(ONSAVEINSTANCESTATE)
  }

  override fun onPause() {
    Timber.d("onPause")
    super.onPause()
    activityStateSubject.onNext(ONPAUSE)
  }

  override fun onStop() {
    Timber.d("onStop")
    super.onStop()
    activityStateSubject.onNext(ONSTOP)
  }

  override fun onDestroy() {
    Timber.d("onDestroy")
    super.onDestroy()
    activityStateSubject.onNext(ONDESTROY)
  }

  override fun onLowMemory() {
    Timber.d("onLowMemory")
    super.onLowMemory()
    activityStateSubject.onNext(ONLOWMMEMORY)
  }

  fun requestPermission() {
//     Here, thisActivity is the current activity
    if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {

      // Permission is not granted
      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
      } else {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 1231231231)

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
      }
    } else {
      // Permission has already been granted
    }
  }

  fun isPermissionGranted(permissionString: String) =
      ContextCompat.checkSelfPermission(this, permissionString) == PackageManager.PERMISSION_GRANTED

  fun askForPermission(permissionString: String) {
    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissionString)) {
      // No explanation needed, we can request the permission.
      ActivityCompat.requestPermissions(this, arrayOf(permissionString), parsePermissionStringToRequestCode(permissionString))
    } else {
      // todo: Show an explanation to the user *asynchronously* -- don't block
      // this thread waiting for the user's response! After the user
      // sees the explanation, try again to request the permission.
    }
  }

  private fun requestPermission(permissionString: String) {
    ActivityCompat.requestPermissions(this, arrayOf(permissionString), permissionString.toInt()) //todo: make tests for permission
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  private fun parsePermissionStringToRequestCode(permissionString: String) =
      permissionString.hashCode().absoluteValue
}
