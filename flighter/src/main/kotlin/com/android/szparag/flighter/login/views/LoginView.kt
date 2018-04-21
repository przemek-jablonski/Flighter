package com.android.szparag.flighter.login.views

import com.android.szparag.flighter.R
import com.android.szparag.flighter.login.states.LoginViewIntent.DialogAcceptanceIntent
import com.android.szparag.flighter.login.states.LoginViewIntent.DialogDismissalIntent
import com.android.szparag.flighter.login.states.LoginViewIntent.LoginRegisterIntent
import com.android.szparag.flighter.login.states.LoginViewIntent.SkipIntent
import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.mvi.navigator.Screen
import com.android.szparag.mvi.util.EmptyIntent
import com.android.szparag.mvi.views.MviView
import io.reactivex.Observable

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
interface LoginView : MviView<LoginViewState> {

  fun loginRegisterIntent(): Observable<LoginRegisterIntent>

  fun skipIntent(): Observable<SkipIntent>

  fun dialogAcceptanceIntent(): Observable<DialogAcceptanceIntent>

  fun dialogDismissalIntent(): Observable<DialogDismissalIntent>

}