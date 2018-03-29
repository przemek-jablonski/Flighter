package com.android.szparag.flighter

import android.content.Context
import android.util.AttributeSet
import com.android.szparag.flighter.ActivityLifecycleState.*
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MapStyleOptions
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_global_flighter.*
import timber.log.Timber

class FlighterGoogleMapView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr) {

  init {
    Timber.d("init")
    getMapAsync { googleMap ->
      Timber.d("init.getMapAsync.callback, googleMap: $googleMap")
      googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.googlemapstyle))
      googleMap.uiSettings.apply {
        setAllGesturesEnabled(false)
      }
    }
  }

  @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
  fun registerActivityStateObservable(activityStateObservable: Observable<ActivityLifecycleState>) {
    Timber.d("registerActivityStateObservable, activityStateObservable: $activityStateObservable")
    activityStateObservable.subscribe { state ->
      Timber.d("registerActivityStateObservable.subscribe, state: $state")
      when(state) {
        ONCREATE -> onCreate(null)
        ONSTART -> onStart()
        ONRESUME -> onResume()
        ONPAUSE -> onPause()
        ONSTOP -> onStop()
        ONDESTROY -> onDestroy()
        ONLOWMMEMORY -> onLowMemory()
        ONSAVEINSTANCESTATE -> onSaveInstanceState(null)
      }
    }
  }


}