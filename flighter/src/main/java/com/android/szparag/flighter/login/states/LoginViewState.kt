package com.android.szparag.flighter.login.states

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
sealed class LoginViewState {

  class OnboardingRegisterViewState : LoginViewState()

  class OnboardingLoginViewState : LoginViewState()

  class AskForCredentialsViewState : LoginViewState()

  class OperationErrorViewState : LoginViewState()

}