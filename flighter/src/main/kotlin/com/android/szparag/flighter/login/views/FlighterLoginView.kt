package com.android.szparag.flighter.login.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.android.szparag.flighter.R
import com.android.szparag.flighter.common.util.Injector
import com.android.szparag.flighter.login.presenters.LoginPresenter
import com.android.szparag.flighter.login.states.LoginViewIntent.DialogAcceptanceIntent
import com.android.szparag.flighter.login.states.LoginViewIntent.DialogDismissalIntent
import com.android.szparag.flighter.login.states.LoginViewIntent.LoginRegisterIntent
import com.android.szparag.flighter.login.states.LoginViewIntent.SkipIntent
import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.AskForCredentialsViewState
import com.android.szparag.flighter.login.states.LoginViewState.LoginSkippedViewState
import com.android.szparag.flighter.login.states.LoginViewState.LoginSuccessfulViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingLoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingRegisterViewState
import com.android.szparag.flighter.login.states.LoginViewState.OperationErrorViewState
import com.android.szparag.flighter.selectdeparture.views.FlighterSelectDepartureView
import com.android.szparag.kotterknife.bindView
import com.android.szparag.mvi.navigator.NavigationTransitionOutPolicy.KILL_ALL_PREVIOUS
import com.android.szparag.mvi.navigator.Screen
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
class FlighterLoginView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : BaseMviConstraintLayout<LoginViewState>(context, attrs, defStyleAttr), LoginView {

  companion object {
    val screenData by lazy {
      Screen(
          viewClass = FlighterLoginView::class.java,
          layoutResource = R.layout.screen_google_login,
          transitionOutPolicy = KILL_ALL_PREVIOUS()
      )
    }
  }

  @Inject
  @Suppress("MemberVisibilityCanBePrivate")
  lateinit var presenter: LoginPresenter

  private val frontImageView: ImageView by bindView(R.id.frontImageView)
  private val descriptionTextView: TextView by bindView(R.id.descriptionTextView)
  private val skipTextView: TextView by bindView(R.id.skipTextView)

  init {
    Timber.d("init")
  }

  override fun loginRegisterIntent(): Observable<LoginRegisterIntent> =
      RxView.clicks(frontImageView).map { LoginRegisterIntent() }

  override fun skipIntent(): Observable<SkipIntent> =
      RxView.clicks(skipTextView).map { SkipIntent() }

  override fun dialogAcceptanceIntent(): Observable<DialogAcceptanceIntent> =
      RxView.clicks(descriptionTextView).map { DialogAcceptanceIntent("Asdasdasd [${System.currentTimeMillis()}") }

  override fun dialogDismissalIntent(): Observable<DialogDismissalIntent> =
      RxView.clicks(descriptionTextView).map { DialogDismissalIntent() }

  override fun onAttachedToWindow() {
    Timber.d("onAttachedToWindow")
    super.onAttachedToWindow()
  }

  override fun onDetachedFromWindow() {
    Timber.d("onDetachedFromWindow")
    super.onDetachedFromWindow()
  }

  override fun render(state: LoginViewState) {
    super.render(state)
    Timber.i("render, state: $state")
    when (state) {
      is OnboardingRegisterViewState -> renderRegisterViewState()
      is OnboardingLoginViewState -> renderLoginViewState()
      is AskForCredentialsViewState -> renderAskForCredentialsViewState()
      is OperationErrorViewState -> renderErrorViewState()
      is LoginSkippedViewState -> renderLoginSkippedViewState()
      is LoginSuccessfulViewState -> renderLoginSuccessfulViewState()
    }
  }

  private fun renderRegisterViewState() {
    Timber.d("renderRegisterViewState")

  }

  private fun renderLoginViewState() {
    Timber.d("renderLoginViewState")

  }

  private fun renderAskForCredentialsViewState() {
    Timber.d("renderAskForCredentialsViewState")

  }

  private fun renderErrorViewState() {
    Timber.d("renderErrorViewState")

  }

  private fun renderLoginSkippedViewState() {
    Timber.d("renderLoginSkippedViewState")
    navigationDelegate.goToScreen(FlighterSelectDepartureView.screenData)
  }

  private fun renderLoginSuccessfulViewState() {
    Timber.d("renderLoginSuccessfulViewState")
    navigationDelegate.goToScreen(FlighterSelectDepartureView.screenData)
  }

  override fun instantiatePresenter() {
    Timber.d("instantiatePresenter")
    Injector.get().inject(this)
  }

  override fun attachToPresenter() {
    Timber.d("attachToPresenter")
    presenter.attachView(this)
  }

  override fun detachFromPresenter() {
    Timber.d("detachFromPresenter")
    presenter.detachView(this)
  }

}
