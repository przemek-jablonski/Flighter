package com.android.szparag.flighter.worldmap.views

import android.content.Context
import android.util.AttributeSet
import com.android.szparag.flighter.ActivityLifecycleState
import com.android.szparag.flighter.ActivityLifecycleState.ONCREATE
import com.android.szparag.flighter.ActivityLifecycleState.ONDESTROY
import com.android.szparag.flighter.ActivityLifecycleState.ONLOWMMEMORY
import com.android.szparag.flighter.ActivityLifecycleState.ONPAUSE
import com.android.szparag.flighter.ActivityLifecycleState.ONRESUME
import com.android.szparag.flighter.ActivityLifecycleState.ONSAVEINSTANCESTATE
import com.android.szparag.flighter.ActivityLifecycleState.ONSTART
import com.android.szparag.flighter.ActivityLifecycleState.ONSTOP
import com.android.szparag.flighter.R.raw
import com.android.szparag.flighter.worldmap.presenters.WorldMapPresenter
import com.android.szparag.flighter.worldmap.states.WorldMapViewState
import com.android.szparag.flighter.worldmap.states.WorldMapViewState.ErrorMapViewState
import com.android.szparag.flighter.worldmap.states.WorldMapViewState.OnboardingMapViewState
import com.android.szparag.flighter.worldmap.states.WorldMapViewState.ShowingLocationMapViewState
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MapStyleOptions
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class FlighterWorldMapView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : MapView(
    context, attrs, defStyleAttr), WorldMapView {

  @Inject
  lateinit var presenter: WorldMapPresenter

  @Volatile
  private var initialized = false
    set(value) {
      field = value
      mapInitializedSubject.onNext(value)
    }

  private val mapInitializedSubject = PublishSubject.create<Boolean>().apply {
    this.doOnSubscribe { this.onNext(initialized) } //todo: is it correctly implemented?
  }


  init {
    Timber.d("init")
    getMapAsync { googleMap ->
      Timber.d("init.getMapAsync.callback, googleMap: $googleMap")
      googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, raw.googlemapstyle))
      googleMap.uiSettings.apply {
        setAllGesturesEnabled(false)
      }
      initialized = true
    }
  }

  override fun mapInitializedIntent(): Observable<Boolean> = mapInitializedSubject

  override fun render(state: WorldMapViewState) {
    Timber.d("render, state: $state")
    when (state) {
      is OnboardingMapViewState      -> {

      }
      is ShowingLocationMapViewState -> {

      }
      is ErrorMapViewState           -> {

      }
    }
  }

  @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
  fun registerActivityStateObservable(activityStateObservable: Observable<ActivityLifecycleState>) {
    Timber.d("registerActivityStateObservable, activityStateObservable: $activityStateObservable")
    activityStateObservable.subscribe { state ->
      Timber.d("registerActivityStateObservable.onNext, state: $state")
      when (state) {
        ONCREATE            -> onCreate(null)
        ONSTART             -> onStart()
        ONRESUME            -> onResume()
        ONPAUSE             -> onPause()
        ONSTOP              -> onStop()
        ONDESTROY           -> onDestroy()
        ONLOWMMEMORY        -> onLowMemory()
        ONSAVEINSTANCESTATE -> onSaveInstanceState(null)
      }
    }
  }


}