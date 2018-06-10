package com.android.szparag.flighter.login.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.android.szparag.columbus.NavigationTransitionOutPolicy.KILL_ALL_PREVIOUS
import com.android.szparag.columbus.Screen
import com.android.szparag.flighter.R
import com.android.szparag.flighter.R.layout
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
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import com.android.szparag.myextensionsbase.exhaustive
import com.jakewharton.rxbinding2.view.RxView
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
class FlighterLoginView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMviConstraintLayout<LoginView, LoginPresenter, LoginViewState>(context, attrs, defStyleAttr),
    LoginView {

  companion object {
    val screenData by lazy {
      Screen(
          viewClass = FlighterLoginView::class.java,
          layoutResource = layout.screen_google_login,
          transitionOutPolicy = KILL_ALL_PREVIOUS()
      )
    }
  }

  @Inject override lateinit var presenter: LoginPresenter
  private val frontImageView: ImageView by bindView(R.id.frontImageView)
  private val descriptionTextView: TextView by bindView(R.id.descriptionTextView)
  private val skipTextView: TextView by bindView(R.id.skipTextView)




  override fun render(state: LoginViewState) {
    super.render(state)
    Timber.i("render, state: $state")
    when (state) {
      is OnboardingRegisterViewState -> Unit
      is OnboardingLoginViewState -> Unit
      is AskForCredentialsViewState -> Unit
      is OperationErrorViewState -> Unit
      is LoginSkippedViewState -> {
        navigationDelegate.goToScreen(FlighterSelectDepartureView.screenData)
      }
      is LoginSuccessfulViewState -> {
        navigationDelegate.goToScreen(FlighterSelectDepartureView.screenData)
      }
    }.exhaustive
  }

  //<editor-fold desc="Intent generation">
  override fun loginRegisterIntent() =
      RxView.clicks(frontImageView).map { LoginRegisterIntent() }

  override fun skipIntent() =
      RxView.clicks(skipTextView).map { SkipIntent() }

  override fun dialogAcceptanceIntent() =
      RxView.clicks(descriptionTextView).map { DialogAcceptanceIntent("Asdasdasd [${System.currentTimeMillis()}") }

  override fun dialogDismissalIntent() =
      RxView.clicks(descriptionTextView).map { DialogDismissalIntent() }
  //</editor-fold>

  override fun instantiatePresenter() = Injector.get().inject(this)

  override fun getScreen(): Screen = screenData

}
