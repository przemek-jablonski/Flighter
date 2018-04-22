package com.android.szparag.flighter.login.presenters

import com.android.szparag.flighter.login.interactors.LoginInteractor
import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.AskForCredentialsViewState
import com.android.szparag.flighter.login.states.LoginViewState.LoginSkippedViewState
import com.android.szparag.flighter.login.states.LoginViewState.LoginSuccessfulViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingLoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingRegisterViewState
import com.android.szparag.flighter.login.views.LoginView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterLoginPresenter @Inject constructor(override var interactor: LoginInteractor)
  : BaseMviPresenter<LoginView, LoginInteractor, LoginViewState>(), LoginPresenter {

  init {
    Timber.d("init")
  }

  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    Timber.d("onFirstViewAttached, view: $view")

    interactor
        .isUserRegistered()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
          interactor.checkIfUserRegistered()
        }
        .subscribe { registered ->
          Timber.d("model.isUserRegistered().onNext, registered: $registered")
          view?.render(if (registered) OnboardingLoginViewState() else OnboardingRegisterViewState())
        }

    processLoginRegisterIntents()
    processSkipIntents()
    processDialogAcceptanceIntents()
    processDialogDismissalIntents()
  }

  private fun processLoginRegisterIntents() {
    Timber.d("processLoginRegisterIntents")
    view?.let {
      it.loginRegisterIntent()
          .subscribeOn(AndroidSchedulers.mainThread())
          .observeOn(Schedulers.single())
          .subscribe { intent ->
            Timber.d("processLoginRegisterIntents.onNext, intent: $intent")
            view?.render(AskForCredentialsViewState())
          }
    }
  }

  private fun processSkipIntents() {
    Timber.d("processSkipIntents")
    view?.let {
      it.skipIntent()
          .subscribeOn(AndroidSchedulers.mainThread())
          .subscribe { intent ->
            Timber.d("processSkipIntents.onNext, intent: $intent")
            view?.render(LoginSkippedViewState())
          }
    }
  }

  private fun processDialogAcceptanceIntents() {
    Timber.d("processDialogAcceptanceIntents")
    view?.let {
      it.dialogAcceptanceIntent()
          .subscribeOn(AndroidSchedulers.mainThread())
          .subscribe { intent ->
            Timber.d("processDialogAcceptanceIntents.onNext, intent: $intent")
            view?.render(LoginSuccessfulViewState())
          }
    }
  }

  private fun processDialogDismissalIntents() {
    Timber.d("processDialogDismissalIntents")
    view?.let {
      it.dialogDismissalIntent()
          .subscribeOn(AndroidSchedulers.mainThread())
          .observeOn(Schedulers.single())
          .subscribe { intent ->
            Timber.d("processDialogDismissalIntents, intent: $intent")
            interactor.checkIfUserRegistered() //todo: why here?
          }
    }
  }


  //____________________________temporary

  override fun onViewAttached(view: LoginView) {
    super.onViewAttached(view)
    Timber.d("onViewAttached, view: $view")
  }

  override fun onViewDetached(view: LoginView) {
    super.onViewDetached(view)
    Timber.d("onViewDetached, view: $view")
  }
}