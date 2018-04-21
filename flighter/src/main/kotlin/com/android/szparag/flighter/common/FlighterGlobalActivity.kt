package com.android.szparag.flighter.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.android.szparag.flighter.R
import com.android.szparag.flighter.common.navigation.FlighterNavigator
import com.android.szparag.flighter.common.util.ActivityLifecycleState
import com.android.szparag.flighter.common.util.ActivityLifecycleState.*
import com.android.szparag.flighter.login.views.FlighterLoginView
import com.android.szparag.kotterknife.bindView
import com.android.szparag.mvi.navigator.MviNavigator
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import io.reactivex.subjects.ReplaySubject
import timber.log.Timber

class FlighterGlobalActivity : AppCompatActivity() {

  //todo: reset buffer after onResume or onStop or onBundle callbacks are called
  private val activityStateSubject = ReplaySubject.create<ActivityLifecycleState>()
  private val globalContainer: FrameLayout by bindView(R.id.globalContainer)
  private val navigationDelegate: MviNavigator by lazy { FlighterNavigator(globalContainer, layoutInflater) } //todo: daggerize dat

  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.d("onCreate, savedInstanceState: $savedInstanceState")
    super.onCreate(savedInstanceState)
    activityStateSubject.onNext(ONCREATE)

    setContentView(R.layout.container_global_flighter)
    instantiateFirstScreens()
  }

  private fun instantiateFirstScreens() {
    navigationDelegate.showScreen(FlighterLoginView.screenData)
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
