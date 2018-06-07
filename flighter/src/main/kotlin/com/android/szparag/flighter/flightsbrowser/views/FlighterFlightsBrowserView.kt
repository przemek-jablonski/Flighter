package com.android.szparag.flighter.flightsbrowser.views

import android.content.Context
import android.util.AttributeSet
import com.android.szparag.columbus.Screen
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.ArrivalDateSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.ChangeDepartureAirportIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.DepartureDateSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserIntent.FlightSelectionIntent
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import io.reactivex.Observable

class FlighterFlightsBrowserView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseMviConstraintLayout<FlightsBrowserViewState>(context, attrs, defStyleAttr), FlightsBrowserView {

  companion object {
    val screenData by lazy {
      Screen(
          viewClass = FlighterFlightsBrowserView::class.java,
          layoutResource =
      )
    }
  }


  override fun instantiatePresenter() {
  }

  override fun attachToPresenter() {
  }

  override fun detachFromPresenter() {
  }

  override fun getScreen(): Screen {
  }

  override fun departureAirportChangeIntent(): Observable<ChangeDepartureAirportIntent> {
  }

  override fun departureDateChangeIntent(): Observable<DepartureDateSelectionIntent> {
  }

  override fun arrivalDateChangeIntent(): Observable<ArrivalDateSelectionIntent> {
  }

  override fun flightSelectionIntent(): Observable<FlightSelectionIntent> {
  }

}