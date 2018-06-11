package com.android.szparag.flighter.flightsbrowser.views

import android.content.Context
import android.util.AttributeSet
import com.android.szparag.columbus.Screen
import com.android.szparag.flighter.R
import com.android.szparag.flighter.common.util.Injector
import com.android.szparag.flighter.flightsbrowser.presenters.FlightsBrowserPresenter
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.ArrivalDateSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.ChangeDepartureAirportIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.DepartureDateSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.FlightSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class FlighterFlightsBrowserView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseMviConstraintLayout<FlightsBrowserView, FlightsBrowserPresenter, FlightsBrowserViewState>(context, attrs, defStyleAttr), FlightsBrowserView {

  companion object {
    val screenData by lazy {
      Screen(
          viewClass = FlighterFlightsBrowserView::class.java,
          layoutResource = R.layout.screen_flights_browser_initial
      )
    }
  }

  //todo: this should be injected in base class maybe?
  //todo: research that
  @Inject override lateinit var presenter: FlightsBrowserPresenter


  override fun departureAirportChangeIntent(): Observable<ChangeDepartureAirportIntent> =
      Observable.never()

  override fun departureDateChangeIntent(): Observable<DepartureDateSelectionIntent> =
      Observable.never()

  override fun arrivalDateChangeIntent(): Observable<ArrivalDateSelectionIntent> =
      Observable.never()

  override fun flightSelectionIntent(): Observable<FlightSelectionIntent> =
      Observable.never()

  override fun render(state: FlightsBrowserViewState) {
    Timber.e("render, state: $state")
    super.render(state)
  }

  override fun instantiatePresenter() = Injector.get().inject(this)

  override fun getScreen() = screenData

}