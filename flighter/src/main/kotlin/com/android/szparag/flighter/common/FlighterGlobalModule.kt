package com.android.szparag.flighter.common

import com.android.szparag.flighter.common.util.ActivityLifecycleState
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.Subject

@Module
class FlighterGlobalModule {

  @Provides
  fun provideActivityStateBus(implementation: GlobalActivityLifecycleBus): Observable<ActivityLifecycleState> = implementation.getBus()

  @Provides
  fun provideActivityStateSubject(implementation: GlobalActivityLifecycleBus): Subject<ActivityLifecycleState> = implementation.getSubject()

}