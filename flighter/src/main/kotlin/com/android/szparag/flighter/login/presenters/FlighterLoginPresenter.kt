package com.android.szparag.flighter.login.presenters

import com.android.szparag.flighter.login.interactors.LoginInteractor
import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.AskForCredentialsViewState
import com.android.szparag.flighter.login.states.LoginViewState.LoginSkippedViewState
import com.android.szparag.flighter.login.states.LoginViewState.LoginSuccessfulViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingLoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingRegisterViewState
import com.android.szparag.flighter.login.views.LoginView
import com.android.szparag.mvi.models.ModelRepository
import com.android.szparag.mvi.presenters.BaseMviPresenter
import com.android.szparag.mvi.util.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterLoginPresenter @Inject constructor(
    override val interactor: LoginInteractor,
    override val modelRepository: ModelRepository<LoginViewState>
) : BaseMviPresenter<LoginView, LoginViewState>(), LoginPresenter {

  override fun distributeFirstViewState() = null

  override fun processUserIntents(view: LoginView) {
    view.loginRegisterIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.single())
        .map { AskForCredentialsViewState() }
        .registerProcessing(this)

    view.skipIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .map { LoginSkippedViewState() }
        .registerProcessing(this)

    view.dialogAcceptanceIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .map { LoginSuccessfulViewState() }
        .registerProcessing(this)

    view.dialogDismissalIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.single())
        .subscribe { interactor.checkIfUserRegistered() } //todo: why here?
        .addTo(presenterDisposables)

  }

  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    Timber.d("onFirstViewAttached")
    interactor
        .isUserRegistered()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { interactor.checkIfUserRegistered() }
        .subscribe { registered ->
          modelRepository.replaceModel(if (registered) OnboardingLoginViewState() else OnboardingRegisterViewState())
        }
        .addTo(presenterDisposables)
  }

}