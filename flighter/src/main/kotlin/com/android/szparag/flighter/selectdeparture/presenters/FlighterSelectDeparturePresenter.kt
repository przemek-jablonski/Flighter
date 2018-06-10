package com.android.szparag.flighter.selectdeparture.presenters

import com.android.szparag.flighter.common.location.LocationFetchingEvent.LocationFetchSuccessful
import com.android.szparag.flighter.selectdeparture.interactors.SelectDepartureInteractor
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.SearchNotStartedViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.SearchResult
import com.android.szparag.flighter.selectdeparture.views.SelectDepartureView
import com.android.szparag.mvi.models.ModelDistributor
import com.android.szparag.mvi.presenters.BaseMviPresenter
import com.szparag.android.mypermissions.PermissionCheckEvent.PermissionGrantedEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

//todo: implement proper threading!!!!!!!!!!!!!!!!
//todo: if fetching has failed, there should be something shown in the UI depending on error type
//todo: https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators
//todo: https://stackoverflow.com/questions/28969995/how-to-ignore-error-and-continue-infinite-stream

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
@Singleton
class FlighterSelectDeparturePresenter @Inject constructor(
    override val interactor: SelectDepartureInteractor,
    override val modelDistributor: ModelDistributor<SelectDepartureViewState>
) : BaseMviPresenter<SelectDepartureView, SelectDepartureViewState>(), SelectDeparturePresenter {

  override fun distributeFirstViewState() = SearchNotStartedViewState()

  //todo: check threading!!!!!!!!!!!!!!!!
  override fun processUserIntents(view: SelectDepartureView) {
    view.searchWithTextIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .filter { it.searchInput.length >= 2 }
        .sample(1000, TimeUnit.MILLISECONDS)
        .flatMap { interactor.getAirportsByCity(it.searchInput) }
        .observeOn(AndroidSchedulers.mainThread())
        .map { list -> SearchResult(list) }
        .registerProcessing(this)

    view.searchWithGpsIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMapSingle { view.requestGpsPermission() }
        .filter { permissionResponse -> permissionResponse is PermissionGrantedEvent } //if permission not granted nothing should be done (including morphing layout)
        .flatMap { interactor.getUserGpsCoordinates() }
        .filter { locationFetchingEvent -> locationFetchingEvent is LocationFetchSuccessful }
        .cast(LocationFetchSuccessful::class.java)
        .flatMap { locationFetchingEvent -> interactor.getAirportsByGpsCoordinates(locationFetchingEvent.coordinates) }
        .observeOn(AndroidSchedulers.mainThread())
        .map { list -> SearchResult(list) }
        .registerProcessing(this)
  }


}