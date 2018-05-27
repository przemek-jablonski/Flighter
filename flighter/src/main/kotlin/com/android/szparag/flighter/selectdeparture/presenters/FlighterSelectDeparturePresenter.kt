package com.android.szparag.flighter.selectdeparture.presenters

import com.android.szparag.flighter.common.location.LocationFetchingEvent.LocationFetchSuccessful
import com.android.szparag.flighter.selectdeparture.interactors.SelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.views.SelectDepartureView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import com.android.szparag.myextensionsbase.emptyString
import com.szparag.android.mypermissions.PermissionCheckEvent.PermissionGrantedEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.cast
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
          .filter { it.searchInput.length >= 2 }
          .sample(1000, TimeUnit.MILLISECONDS)
          .flatMap { interactor.getAirportsByCity(it.searchInput).map { list -> SelectDepartureViewState.SearchResult(list) } }
          .subscribe {
            Timber.d("processSearchWithTextIntent.onNext, event: $it")
            view?.render(it)
          }
    }
  }

  private fun processSearchWithGpsIntent() {
    Timber.d("processSearchWithGpsIntent")
    view?.let { view -> //todo: 'view ->' should be everywhere
      view.searchWithGpsIntent()
          .subscribeOn(AndroidSchedulers.mainThread())
          .observeOn(AndroidSchedulers.mainThread())
          .flatMapSingle { view.requestGpsPermission() }
          //if permission not granted nothing should be done (including morphing layout)
          .filter { permissionResponse -> permissionResponse is PermissionGrantedEvent }
          .flatMap { interactor.getUserGpsCoordinates() }
          //todo: if fetching has failed, there should be something shown in the UI depending on error type
          //todo: https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators
          //todo: https://stackoverflow.com/questions/28969995/how-to-ignore-error-and-continue-infinite-stream
          .filter { locationFetchingEvent -> locationFetchingEvent is LocationFetchSuccessful }
          .cast(LocationFetchSuccessful::class.java) //todo: use this where applicable (at least one place)
          .flatMap { locationFetchingEvent -> interactor.getAirportsByGpsCoordinates(locationFetchingEvent.coordinates) }
          .subscribe {
            Timber.d("processSearchWithGpsIntent.onNext, event: $it")
            view.render(SelectDepartureViewState.FetchingResultWithGpsViewState())
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