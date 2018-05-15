package com.android.szparag.flighter.common

import com.android.szparag.flighter.common.util.ActivityLifecycleState
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalActivityLifecycleBus @Inject constructor() {

  private val lifecycleSubject = ReplaySubject.create<ActivityLifecycleState>()

  fun getSubject(): Subject<ActivityLifecycleState> = lifecycleSubject
  fun getBus(): Observable<ActivityLifecycleState> = lifecycleSubject

}