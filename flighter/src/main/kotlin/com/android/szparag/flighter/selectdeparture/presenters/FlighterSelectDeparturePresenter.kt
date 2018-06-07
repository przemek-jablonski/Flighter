package com.android.szparag.flighter.selectdeparture.presenters

import com.android.szparag.flighter.common.location.LocationFetchingEvent.LocationFetchSuccessful
import com.android.szparag.flighter.selectdeparture.interactors.SelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.SearchResult
import com.android.szparag.flighter.selectdeparture.views.SelectDepartureView
import com.android.szparag.mvi.presenters.BaseMviPresenter
import com.android.szparag.myextensionsbase.emptyString
import com.szparag.android.mypermissions.PermissionCheckEvent.PermissionGrantedEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterSelectDeparturePresenter @Inject constructor(override val interactor: SelectDepartureInteractor)
  : BaseMviPresenter<SelectDepartureView, SelectDepartureInteractor, SelectDepartureViewState>(), SelectDeparturePresenter {

  private var cachedSearchTextInput = emptyString()
  private lateinit var intentsDisposable: CompositeDisposable //todo: this should be in BaseMviPresenter

  init {
    Timber.d("init")
  }


  override fun onFirstViewAttached() {
    super.onFirstViewAttached()
    Timber.d("onFirstViewAttached")
  }

  override fun onViewAttached(view: SelectDepartureView) {
    super.onViewAttached(view)
    Timber.d("onViewAttached, view: $view")
    intentsDisposable = CompositeDisposable()
    view.render(SelectDepartureViewState.SearchNotStartedViewState())
    processSearchWithTextIntent(view)
    processSearchWithGpsIntent(view)
  }

  override fun onViewDetached(view: SelectDepartureView) {
    super.onViewDetached(view)
    Timber.d("onViewDetached, view: $view")
    intentsDisposable.clear()
  }

  //todo: check threading!!!!!!!!!!!!!!!!
  private fun processSearchWithTextIntent(view: SelectDepartureView) {
    Timber.d("processSearchWithTextIntent")
    view.searchWithTextIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .filter { it.searchInput.length >= 2 }
        .sample(1000, TimeUnit.MILLISECONDS)
        .flatMap { interactor.getAirportsByCity(it.searchInput) }
        .map { list -> SearchResult(list) }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { viewState ->
          Timber.d("processSearchWithTextIntent.onNext, viewState: $viewState")
          view.render(viewState)
        }
        .addTo(intentsDisposable)

  }

  //todo: check threading!!!!!!!!!!!!!!!!
  //todo: if fetching has failed, there should be something shown in the UI depending on error type
  //todo: https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators
  //todo: https://stackoverflow.com/questions/28969995/how-to-ignore-error-and-continue-infinite-stream
  private fun processSearchWithGpsIntent(view: SelectDepartureView) {
    Timber.d("processSearchWithGpsIntent")
    view.searchWithGpsIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMapSingle { view.requestGpsPermission() }
        .filter { permissionResponse -> permissionResponse is PermissionGrantedEvent } //if permission not granted nothing should be done (including morphing layout)
        .flatMap { interactor.getUserGpsCoordinates() }
//        .observeOn(Schedulers.io())
        .filter { locationFetchingEvent -> locationFetchingEvent is LocationFetchSuccessful }
        .cast(LocationFetchSuccessful::class.java)
        .flatMap { locationFetchingEvent -> interactor.getAirportsByGpsCoordinates(locationFetchingEvent.coordinates) }
        .map { list -> SearchResult(list) }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { viewState ->
          Timber.d("processSearchWithGpsIntent.onNext, viewState: $viewState")
          view.render(viewState)
        }
        .addTo(intentsDisposable)
  }

}