package com.android.szparag.flighter.login.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.android.szparag.flighter.R
import com.android.szparag.flighter.common.util.Injector
import com.android.szparag.flighter.login.presenters.LoginPresenter
import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.AskForCredentialsViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingLoginViewState
import com.android.szparag.flighter.login.states.LoginViewState.OnboardingRegisterViewState
import com.android.szparag.flighter.login.states.LoginViewState.OperationErrorViewState
import com.android.szparag.kotterknife.bindView
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
class FlighterLoginView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(
    context, attrs, defStyleAttr), LoginView {

  @Inject
  lateinit var presenter: LoginPresenter

  private val frontImageView: ImageView by bindView(R.id.frontImageView)
  private val descriptionTextView: TextView by bindView(R.id.descriptionTextView)
  private val skipTextView: TextView by bindView(R.id.skipTextView)

  init {
    Timber.d("init")
    Injector.get().inject(this)
  }

  override fun render(state: LoginViewState) {
    Timber.d("render, state: $state")
    when (state) {
      is OnboardingRegisterViewState -> {

      }
      is OnboardingLoginViewState    -> {

      }
      is AskForCredentialsViewState  -> {

      }
      is OperationErrorViewState     -> {

      }
    }
  }


}