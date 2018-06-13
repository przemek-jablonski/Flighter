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
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState.FlightDetailsViewState
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState.FlightsListingViewState
import com.android.szparag.flighter.flightsbrowser.states.FlightsBrowserViewState.SearchNotStartedViewState
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import com.android.szparag.myextensionsandroid.show
import com.android.szparag.myextensionsbase.UnixTimestamp
import com.android.szparag.myextensionsbase.exhaustive
import com.android.szparag.myextensionsbase.invalidLongValue
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import kotlinx.android.synthetic.main.screen_flights_browser_initial.view.screen_flights_browser_airport_header as airportHeaderTextView
import kotlinx.android.synthetic.main.screen_flights_browser_initial.view.screen_flights_browser_arrival_date_edittext as arrivalDateEditText
import kotlinx.android.synthetic.main.screen_flights_browser_initial.view.screen_flights_browser_departure_date_edittext as departureDateEditText

class FlighterFlightsBrowserView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseMviConstraintLayout<FlightsBrowserView, FlightsBrowserPresenter, FlightsBrowserViewState>(context, attrs,
    defStyleAttr), FlightsBrowserView {

  companion object {
    val screenData by lazy {
      Screen(
          viewClass = FlighterFlightsBrowserView::class.java,
          layoutResource = R.layout.screen_flights_browser_initial
      )
    }
  }

  @Inject override lateinit var presenter: FlightsBrowserPresenter
  @Inject lateinit var simpleDateFormat: SimpleDateFormat

  override fun departureAirportChangeIntent(): Observable<ChangeDepartureAirportIntent> =
      Observable.never() //todo: implement back button

  override fun departureDateChangeIntent(): Observable<DepartureDateSelectionIntent> =
      RxView.focusChanges(departureDateEditText)
          .filter { it }
          .map { DepartureDateSelectionIntent(convertDateStringToUnixTimestamp(departureDateEditText.text.toString())) }

  override fun arrivalDateChangeIntent(): Observable<ArrivalDateSelectionIntent> =
      RxView.focusChanges(arrivalDateEditText)
          .filter { it }
          .map { ArrivalDateSelectionIntent(convertDateStringToUnixTimestamp(arrivalDateEditText.text.toString())) }

  override fun flightSelectionIntent(): Observable<FlightSelectionIntent> =
      Observable.never()

  override fun render(state: FlightsBrowserViewState) {
    super.render(state)
    when (state) {
      is SearchNotStartedViewState -> {
        airportHeaderTextView.show()
        arrivalDateEditText.show()
        departureDateEditText.show()
        airportHeaderTextView.text = state.airportName
        state.arrivalDate
            ?.takeIf { unixTimestamp -> unixTimestamp != invalidLongValue() }
            ?.let { timestamp ->
              arrivalDateEditText.setText(convertUnixTimestampToDateString(timestamp))
            }
        state.departureDate
            ?.takeIf { unixTimestamp -> unixTimestamp != invalidLongValue() }
            ?.let { timestamp ->
              departureDateEditText.setText(convertUnixTimestampToDateString(timestamp))
            }
      }
      is FlightsListingViewState -> Unit
      is FlightDetailsViewState -> Unit
    }.exhaustive
  }

  override fun instantiatePresenter() = Injector.get().inject(this)

  override fun getScreen() = screenData

  //todo: does it work with different time zone?
  private fun convertUnixTimestampToDateString(unixTimestamp: UnixTimestamp) =
      simpleDateFormat.format(Date(unixTimestamp * 1000L))

  private fun convertDateStringToUnixTimestamp(dateString: String): UnixTimestamp =
      if (dateString.isNotBlank()) simpleDateFormat.parse(dateString).time / 1000L else invalidLongValue()

}