package com.android.szparag.flighter.login.interactors

import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.mvi.models.MviModel
import io.reactivex.Observable

interface LoginInteractor : MviModel<LoginViewState> {

  fun checkIfUserRegistered()

  fun isUserRegistered(): Observable<Boolean>

  fun processRegistrationCredentials(email: String): Observable<Boolean>
  

}