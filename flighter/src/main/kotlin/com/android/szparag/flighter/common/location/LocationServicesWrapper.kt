package com.android.szparag.flighter.common.location

import android.annotation.SuppressLint
import android.location.Location
import com.android.szparag.flighter.common.location.LocationFetchingEvent.LocationFetchAbortedNoPermission
import com.android.szparag.flighter.common.location.LocationFetchingEvent.LocationFetchCancelled
import com.android.szparag.flighter.common.location.LocationFetchingEvent.LocationFetchFailed
import com.android.szparag.flighter.common.location.LocationFetchingEvent.LocationFetchSuccessful
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import com.szparag.android.mypermissions.AndroidPermission.AccessCoarseLocationPermission
import com.szparag.android.mypermissions.AndroidPermission.AccessFineLocationPermission
import com.szparag.android.mypermissions.PermissionManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import timber.log.Timber
import javax.inject.Inject

class LocationServicesWrapper @Inject constructor(val locationProviderClient: FusedLocationProviderClient, val permissionManager: PermissionManager) {

  private val lastLocationSubject = PublishSubject.create<LocationFetchingEvent>()

  fun isLocationPermissionsGranted(): Boolean =
      permissionManager.isPermissionAlreadyGranted(locationProviderClient.applicationContext, AccessFineLocationPermission) ||
          permissionManager.isPermissionAlreadyGranted(locationProviderClient.applicationContext, AccessCoarseLocationPermission)
              .also { Timber.d("isLocationPermissionsGranted, result: $it") }

  @SuppressLint("MissingPermission")
  fun getLocation(): Observable<LocationFetchingEvent> {
    Timber.d("getLocation")
    return if (isLocationPermissionsGranted()) {
      locationProviderClient
          .lastLocation
          .listenForLocationEvent(lastLocationSubject)
    } else Observable.just(LocationFetchAbortedNoPermission())
  }

  private fun Task<Location>.listenForLocationEvent(subject: Subject<LocationFetchingEvent>): Subject<LocationFetchingEvent> {
    Timber.d("listenForLocationEvent, subject: $subject")
    this
        .addOnSuccessListener { location ->
          Timber.i("listenForLocationEvent.addOnSuccessListener, location: $location")
          subject.onNext(LocationFetchSuccessful(WorldCoordinates(location.latitude, location.longitude, location.accuracy)))
        }
        .addOnFailureListener { exception ->
          Timber.w("listenForLocationEvent.addOnFailureListener, exception: $exception")
          subject.onNext(LocationFetchFailed(exception))
        }
        .addOnCanceledListener {
          Timber.w("listenForLocationEvent.addOnCanceledListener")
          subject.onNext(LocationFetchCancelled())
        }
    return subject
  }

}