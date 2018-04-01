package com.android.szparag.flighter.login.views

import com.android.szparag.flighter.login.states.LoginViewState

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
interface LoginView {

  fun render(state: LoginViewState)
}