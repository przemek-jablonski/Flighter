package com.android.szparag.flighter.login

import com.android.szparag.flighter.login.interactors.FlighterLoginInteractor
import com.android.szparag.flighter.login.interactors.LoginInteractor
import com.android.szparag.flighter.login.presenters.FlighterLoginPresenter
import com.android.szparag.flighter.login.presenters.LoginPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Module
class LoginModule {

  @Provides
  fun providePresenter(implementation: FlighterLoginPresenter): LoginPresenter = implementation

  @Provides
  fun provideInteractor(implementation: FlighterLoginInteractor): LoginInteractor = implementation

}