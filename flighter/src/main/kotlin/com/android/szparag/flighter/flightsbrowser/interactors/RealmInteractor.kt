package com.android.szparag.flighter.flightsbrowser.interactors

import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmModel

class RealmInteractor {

  private lateinit var realmInstance: Realm

  init {
    getRealm().where(RealmModel::class.java).findAll().asFlowable()
        .subscribeOn(Schedulers.single())
  }

  protected fun getRealm() =
      if (::realmInstance.isInitialized) realmInstance else Realm.getDefaultInstance()

}