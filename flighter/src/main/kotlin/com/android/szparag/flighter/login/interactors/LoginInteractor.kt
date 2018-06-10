package com.android.szparag.flighter.login.interactors

import com.android.szparag.mvi.interactors.MviInteractor
import io.reactivex.Observable

interface LoginInteractor : MviInteractor {

  fun checkIfUserRegistered()

  fun isUserRegistered(): Observable<Boolean>

  fun processRegistrationCredentials(email: String): Observable<Boolean>

}