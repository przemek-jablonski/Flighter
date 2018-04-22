package com.android.szparag.flighter.selectdeparture

import com.android.szparag.flighter.selectdeparture.interactors.FlighterSelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.interactors.SelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.presenters.FlighterSelectDeparturePresenter
import com.android.szparag.flighter.selectdeparture.presenters.SelectDeparturePresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Module
class SelectDepartureModule {

  @Provides
  fun providePresenter(implementation: FlighterSelectDeparturePresenter): SelectDeparturePresenter = implementation


  @Provides
  fun provideInteractor(implementation: FlighterSelectDepartureInteractor): SelectDepartureInteractor = implementation

}