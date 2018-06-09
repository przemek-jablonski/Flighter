package com.android.szparag.flighter.common.preferences

import io.reactivex.Flowable

class FlighterUserSettingsRepository : UserSettingsRepository {
  override fun update(item: UserSettingsModel) {
    //todo: implement
  }

  override fun getItem(): Flowable<out List<UserSettingsModel>> =
      Flowable.never() //todo: implement

}