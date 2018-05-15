package com.android.szparag.flighter.login.states

//todo: is is nessesary?
sealed class LoginViewIntent {

  class LoginRegisterIntent : LoginViewIntent()

  class SkipIntent : LoginViewIntent()

  data class DialogAcceptanceIntent(val email: String) : LoginViewIntent()

  class DialogDismissalIntent : LoginViewIntent()

}