package com.android.szparag.flighter.common.preferences

import io.reactivex.Flowable
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlighterUserPreferencesRepository @Inject constructor() : UserPreferencesRepository {

  override fun update(item: UserPreferencesModel) {
    with(Realm.getDefaultInstance()) {
      executeTransaction({
        insertOrUpdate(item)
        close()
      })
    }
  }

  override fun getItem(): Flowable<out List<UserPreferencesModel>> =
      Realm.getDefaultInstance().where(UserPreferencesModel::class.java).findAll().asFlowable()

}