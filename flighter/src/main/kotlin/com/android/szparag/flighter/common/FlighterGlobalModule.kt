package com.android.szparag.flighter.common

import com.android.szparag.flighter.common.util.ActivityLifecycleState
import com.google.firebase.database.FirebaseDatabase
import com.szparag.android.mypermissions.PermissionManager
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import javax.inject.Singleton

@Module
class FlighterGlobalModule {

  @Provides
  fun provideActivityStateBus(implementation: GlobalActivityLifecycleBus): Observable<ActivityLifecycleState> = implementation.getBus()

  @Provides
  fun provideActivityStateSubject(implementation: GlobalActivityLifecycleBus): Subject<ActivityLifecycleState> = implementation.getSubject()

  @Provides
  @Singleton
  fun provideFirebaseDatabaseReference() = FirebaseDatabase.getInstance().reference

  @Provides
  @Singleton
  fun providePermissionManager() = PermissionManager()

}