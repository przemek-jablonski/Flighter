package com.android.szparag.flighter.selectdeparture.presenters

import com.android.szparag.flighter.selectdeparture.interactors.SelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.views.SelectDepartureView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import com.android.szparag.myextensionsbase.emptyString
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterSelectDeparturePresenter @Inject constructor(override var interactor: SelectDepartureInteractor)
  : BaseMviPresenter<SelectDepartureView, SelectDepartureInteractor, SelectDepartureViewState>(), SelectDeparturePresenter {

  private var cachedSearchTextInput = emptyString()

  init {
    Timber.d("init")
  }


  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    Timber.d("onFirstViewAttached")
    view?.render(SelectDepartureViewState.SearchNotStartedViewState())
    processSearchWithTextIntent()
    processSearchWithGpsIntent()
  }

  private fun processSearchWithTextIntent() {
    Timber.d("processSearchWithTextIntent")
    view?.let {
      it.searchWithTextIntent()
          .subscribeOn(AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
//          .filter { intent -> intent.searchInput.length > cachedSearchTextInput.length }
//          .doOnEach {
//            cachedSearchTextInput =
//          }
          .filter { it.searchInput.length >= 2 }
          .sample(1000, TimeUnit.MILLISECONDS)
          .switchMap { interactor.getAirportsByCity(it.searchInput).map { list -> SelectDepartureViewState.SearchResult(list) } }
          .subscribe {
            Timber.d("processSearchWithTextIntent.onNext, event: $it")
            view?.render(it)
          }
    }
  }

  private fun processSearchWithGpsIntent() {
    Timber.d("processSearchWithGpsIntent")
    view?.let {
      it.searchWithGpsIntent()
          .subscribeOn(AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe {
            Timber.d("processSearchWithGpsIntent.onNext, event: $it")
            view?.render(SelectDepartureViewState.FetchingResultWithGpsViewState())
          }
    }
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