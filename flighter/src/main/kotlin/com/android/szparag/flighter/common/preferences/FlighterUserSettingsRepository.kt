package com.android.szparag.flighter.common.preferences

import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlighterUserSettingsRepository @Inject constructor() : UserSettingsRepository {
  override fun update(item: UserSettingsModel) {
    //todo: implement
  }

  override fun getItem(): Flowable<out List<UserSettingsModel>> =
      Flowable.never() //todo: implement

}