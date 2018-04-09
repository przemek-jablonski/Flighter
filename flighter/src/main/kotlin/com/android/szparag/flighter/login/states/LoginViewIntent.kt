package com.android.szparag.flighter.login.states

sealed class LoginViewIntent {
  override fun toString(): String {
    val string = super.toString()
    return string
  }

  class LoginRegisterIntent: LoginViewIntent()
  class SkipIntent: LoginViewIntent()

  data class DialogAcceptanceIntent(val email: String): LoginViewIntent()

  class DialogDismissalIntent: LoginViewIntent()

}