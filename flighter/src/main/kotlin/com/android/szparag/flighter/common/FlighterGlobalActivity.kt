package com.android.szparag.flighter.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import com.android.szparag.columbus.ColumbusNavigationRoot
import com.android.szparag.columbus.ColumbusNavigator
import com.android.szparag.columbus.Navigator
import com.android.szparag.flighter.R
import com.android.szparag.flighter.common.permissions.AndroidPermission
import com.android.szparag.flighter.common.permissions.PermissionManager
import com.android.szparag.flighter.common.util.ActivityLifecycleState
import com.android.szparag.flighter.common.util.ActivityLifecycleState.*
import com.android.szparag.flighter.common.util.Injector
import com.android.szparag.flighter.login.views.FlighterLoginView
import com.android.szparag.flighter.worldmap.views.FlighterWorldMapView
import com.android.szparag.kotterknife.bindView
import io.reactivex.subjects.Subject
import timber.log.Timber
import javax.inject.Inject

class FlighterGlobalActivity : AppCompatActivity(), ColumbusNavigationRoot {

  //todo: reset buffer after onResume or onStop or onBundle callbacks are called
  @Inject
  lateinit var activityStateSubject: Subject<ActivityLifecycleState>

  @Inject
  lateinit var permissionManager: PermissionManager

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

  fun requestPermission(permission: AndroidPermission) {
    Timber.d("requestPermission, permission: $permission")
    permissionManager.requestPermission(this, permission.androidPermissionString)
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    Timber.d("onRequestPermissionsResult, requestCode: $requestCode, permissions: $permissions, grantResults: $grantResults")
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

}
