package com.android.szparag.flighter.login.interactors

import io.reactivex.Observable
import timber.log.Timber
import java.util.Random

class FlighterLoginInteractor : LoginInteractor {
  private var random = Random()

  init {
    Timber.d("[${hashCode()}]: null")
  }

  override fun checkIfUserRegistered() {
    Timber.d("[${hashCode()}]: checkIfUserRegistered")
  }

  override fun isUserRegistered(): Observable<Boolean> {
    Timber.d("[${hashCode()}]: isUserRegistered")
    return Observable.just(random.nextBoolean())
  }

  override fun processRegistrationCredentials(email: String): Observable<Boolean> {
    Timber.d("[${hashCode()}]: processRegistrationCredentials, email: $email")
    return Observable.just(true)
  }
}