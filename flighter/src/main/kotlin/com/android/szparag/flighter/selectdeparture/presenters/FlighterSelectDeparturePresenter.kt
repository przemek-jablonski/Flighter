package com.android.szparag.flighter.selectdeparture.presenters

import com.android.szparag.flighter.selectdeparture.interactors.SelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.views.SelectDepartureView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterSelectDeparturePresenter @Inject constructor(override var interactor: SelectDepartureInteractor)
  : BaseMviPresenter<SelectDepartureView, SelectDepartureInteractor, SelectDepartureViewState>(), SelectDeparturePresenter {

  init {
    Timber.d("init")
  }


  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    Timber.d("onFirstViewAttached")
    view?.render(SelectDepartureViewState.SearchNotStartedViewState())
  }


  //____________________________temporary

  override fun onViewAttached(view: SelectDepartureView) {
    super.onViewAttached(view)
    Timber.d("onViewAttached, view: $view")
  }

  override fun onViewDetached(view: SelectDepartureView) {
    super.onViewDetached(view)
    Timber.d("onViewDetached, view: $view")
  }
}