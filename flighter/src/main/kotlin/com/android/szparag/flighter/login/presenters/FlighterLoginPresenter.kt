package com.android.szparag.flighter.login.presenters

import com.android.szparag.flighter.login.interactors.LoginInteractor
import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingLoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingRegisterViewState
import com.android.szparag.flighter.login.views.LoginView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import com.android.szparag.myextensionsrx.computation
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterLoginPresenter @Inject constructor() : BaseMviPresenter<LoginView, LoginInteractor, LoginViewState>(), LoginPresenter {

  init {
    Timber.d("[${hashCode()}]: init")
  }


  override fun onFirstViewAttached() {
    Timber.d("[${hashCode()}]: onFirstViewAttached, view: $view")
    requireNotNull(view)
    
    model
      .isUserRegistered()
      .computation()
      .doOnSubscribe {
        Timber.d("[${hashCode()}]: model.isUserRegistered().onSubscribe")
        model.checkIfUserRegistered()
      }
      .subscribe { registered ->
        Timber.d("[${hashCode()}]: model.isUserRegistered().onNext, registered: $registered")
        view?.render(if (registered) OnboardingLoginViewState() else OnboardingRegisterViewState())
      }
    
    processLoginRegisterIntent()
    processSkipIntent()
    processDialogAcceptanceIntent()
    processDialogDismissalIntent()

  }

  private fun processLoginRegisterIntent() {
    Timber.d("[${hashCode()}]: processLoginRegisterIntent")
    view
      ?.loginRegisterIntent()
      ?.computation()
      ?.subscribe {intent ->
        Timber.d("[${hashCode()}]: processLoginRegisterIntent.onNext, intent: $intent")
      }
  }

  private fun processSkipIntent() {
    Timber.d("[${hashCode()}]: processSkipIntent")
    view
      ?.skipIntent()
      ?.computation()
      ?.subscribe { intent ->
        Timber.d("[${hashCode()}]: processSkipIntent.onNext, intent: $intent")
      }
  }

  private fun processDialogAcceptanceIntent() {
    Timber.d("[${hashCode()}]: processDialogAcceptanceIntent")
    view
      ?.dialogAcceptanceIntent()
      ?.computation()
      ?.subscribe { intent ->
        Timber.d("[${hashCode()}]: processDialogAcceptanceIntent.onNext, intent: $intent")
      }
  }

  private fun processDialogDismissalIntent() {
    Timber.d("[${hashCode()}]: processDialogDismissalIntent")
    view
      ?.dialogDismissalIntent()
      ?.computation()
      ?.subscribe { intent ->
        Timber.d("[${hashCode()}]: processDialogDismissalIntent, intent: $intent")
        model.checkIfUserRegistered()
      }
  }

}