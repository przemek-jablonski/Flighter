package com.android.szparag.flighter.login

import com.android.szparag.flighter.login.interactors.FlighterLoginInteractor
import com.android.szparag.flighter.login.interactors.LoginInteractor
import com.android.szparag.flighter.login.presenters.FlighterLoginPresenter
import com.android.szparag.flighter.login.presenters.LoginPresenter
import com.android.szparag.flighter.login.states.LoginViewState
import com.android.szparag.mvi.models.BaseMviModelRepository
import com.android.szparag.mvi.models.ModelRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Module
class LoginModule {

  @Provides
  @Singleton
  fun provideWorldMapViewStateDistributor(): ModelRepository<LoginViewState> = BaseMviModelRepository()

  @Provides
  fun providePresenter(implementation: FlighterLoginPresenter): LoginPresenter = implementation

  @Provides
  fun provideInteractor(implementation: FlighterLoginInteractor): LoginInteractor = implementation

}