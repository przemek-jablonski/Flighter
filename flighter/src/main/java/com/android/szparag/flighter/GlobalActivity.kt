package com.android.szparag.flighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activity_global.*

class GlobalActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_global)
    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync { googleMap ->
      googleMap.uiSettings.apply {
        setAllGesturesEnabled(false)
      }
    }
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

}
