package com.android.szparag.flighter.login.views

import android.content.Context
import android.support.design.widget.Snackbar
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
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingLoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingRegisterViewState
import com.android.szparag.flighter.login.states.LoginViewState.OperationErrorViewState
import com.android.szparag.kotterknife.bindView
import com.android.szparag.mvi.util.EmptyIntent
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


  @Inject
  @Suppress("MemberVisibilityCanBePrivate")
  lateinit var presenter: LoginPresenter

  private val frontImageView: ImageView by bindView(R.id.frontImageView)
  private val descriptionTextView: TextView by bindView(R.id.descriptionTextView)
  private val skipTextView: TextView by bindView(R.id.skipTextView)

  init {
    Timber.d("[${hashCode()}]: init")
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
    Timber.d("[${hashCode()}]: onAttachedToWindow")
    super.onAttachedToWindow()
  }

  override fun onDetachedFromWindow() {
    Timber.d("[${hashCode()}]: onDetachedFromWindow")
    super.onDetachedFromWindow()
  }

  override fun render(state: LoginViewState) {
    super.render(state)
    Timber.i("[${hashCode()}]: render, state: $state")
    when (state) {
      is OnboardingRegisterViewState -> renderRegisterViewState()
      is OnboardingLoginViewState    -> renderLoginViewState()
      is AskForCredentialsViewState  -> renderAskForCredentialsViewState()
      is OperationErrorViewState     -> renderErrorViewState()
    }
  }

  private fun renderRegisterViewState() {
    Timber.d("[${hashCode()}]: renderRegisterViewState")

  }

  private fun renderLoginViewState() {
    Timber.d("[${hashCode()}]: renderLoginViewState")

  }

  private fun renderAskForCredentialsViewState() {
    Timber.d("[${hashCode()}]: renderAskForCredentialsViewState")

  }

  private fun renderErrorViewState() {
    Timber.d("[${hashCode()}]: renderErrorViewState")

  }

  override fun instantiatePresenter() {
    Timber.d("[${hashCode()}]: instantiatePresenter")
    Injector.get().inject(this)
  }

  override fun attachToPresenter() {
    Timber.d("[${hashCode()}]: attachToPresenter")
    presenter.onViewAttached(this)
  }

  override fun detachFromPresenter() {
    Timber.d("[${hashCode()}]: detachFromPresenter")
    presenter.onViewDetached(this)
  }

}
