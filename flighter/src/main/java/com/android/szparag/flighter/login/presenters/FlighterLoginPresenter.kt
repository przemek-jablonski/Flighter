package com.android.szparag.flighter.login.presenters

import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.flighter.login.views.LoginView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterLoginPresenter @Inject constructor() : BaseMviPresenter<LoginView, LoginViewState>(), LoginPresenter {

  init {
    Timber.d("init")
  }

  override fun onViewAttached(view: LoginView) {
    Timber.d("onViewAttached, view: $view")
  }

  override fun onViewDetached(view: LoginView) {
    Timber.d("onViewDetached, view: $view")
  }

}