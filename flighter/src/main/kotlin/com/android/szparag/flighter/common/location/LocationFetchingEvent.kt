package com.android.szparag.flighter.common.location

sealed class LocationFetchingEvent {

  data class LocationFetchSuccessful(val coordinates: WorldCoordinates) : LocationFetchingEvent()

  data class LocationFetchFailed(val exception: Exception) : LocationFetchingEvent()

  class LocationFetchCancelled: LocationFetchingEvent()

  class LocationFetchAbortedNoPermission: LocationFetchingEvent()

}