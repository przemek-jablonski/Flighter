package com.android.szparag.flighter.login.presenters

import com.android.szparag.flighter.login.interactors.LoginInteractor
import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.flighter.login.views.LoginView
import com.android.szparag.mvi.presenters.MviPresenter

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
interface LoginPresenter : MviPresenter<LoginView, LoginViewState>