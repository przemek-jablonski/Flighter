package com.android.szparag.flighter

import android.content.Context
import android.util.AttributeSet
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.android.synthetic.main.layout_global_flighter.*

class FlighterGoogleMapView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr) {

  init {
    getMapAsync { googleMap ->
      googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.googlemapstyle))
      googleMap.uiSettings.apply {
        setAllGesturesEnabled(false)
      }
    }
  }


}