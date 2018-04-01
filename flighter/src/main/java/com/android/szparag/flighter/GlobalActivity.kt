package com.android.szparag.flighter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.android.szparag.flighter.base.ActivityLifecycleState
import com.android.szparag.flighter.base.ActivityLifecycleState.ONCREATE
import com.android.szparag.flighter.base.ActivityLifecycleState.ONDESTROY
import com.android.szparag.flighter.base.ActivityLifecycleState.ONLOWMMEMORY
import com.android.szparag.flighter.base.ActivityLifecycleState.ONPAUSE
import com.android.szparag.flighter.base.ActivityLifecycleState.ONRESUME
import com.android.szparag.flighter.base.ActivityLifecycleState.ONSAVEINSTANCESTATE
import com.android.szparag.flighter.base.ActivityLifecycleState.ONSTART
import com.android.szparag.flighter.base.ActivityLifecycleState.ONSTOP
import com.android.szparag.flighter.worldmap.views.FlighterWorldMapView
import com.android.szparag.kotterknife.bindView
import io.reactivex.subjects.ReplaySubject
import timber.log.Timber

class GlobalActivity : AppCompatActivity() {

  //todo: reset buffer after onResume or onStop or onBundle callbacks are called
  private val activityStateSubject = ReplaySubject.create<ActivityLifecycleState>()
  private val globalContainer: FrameLayout by bindView(R.id.globalContainer)


  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.d("onCreate, savedInstanceState: $savedInstanceState")
    super.onCreate(savedInstanceState)
    activityStateSubject.onNext(ONCREATE)
    Injector.get().inject(this)

    setContentView(R.layout.layout_global_flighter)
    constructBackground(globalContainer)
    constructFirstScreen(globalContainer)
  }

  private fun constructBackground(container: FrameLayout) {
    Timber.d("constructBackground, container: $container")
    val googleMapView = layoutInflater.inflate(R.layout.layout_google_map, container, false) as FlighterWorldMapView
    globalContainer.addView(googleMapView)
    googleMapView.registerActivityStateObservable(activityStateSubject)
  }

  private fun constructFirstScreen(container: FrameLayout) {
    Timber.d("constructFirstScreen, container: $container")
    globalContainer.addView(layoutInflater.inflate(R.layout.layout_google_login, container, false))
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

}
