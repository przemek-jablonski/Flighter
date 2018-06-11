package com.android.szparag.flighter.selectdeparture.views

import android.content.Context
import android.support.constraint.ConstraintSet
import android.support.v7.widget.LinearLayoutManager
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import com.android.szparag.columbus.Screen
import com.android.szparag.flighter.R
import com.android.szparag.flighter.common.util.Injector
import com.android.szparag.flighter.flightsbrowser.views.FlighterFlightsBrowserView
import com.android.szparag.flighter.selectdeparture.AirportsAdapter
import com.android.szparag.flighter.selectdeparture.presenters.SelectDeparturePresenter
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureIntent.AirportSelectionIntent
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureIntent.GpsSearchIntent
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureIntent.TextSearchIntent
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.AirportSelectedViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.EmptySearchResult
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.FetchingResultWithGpsViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.FetchingResultWithTextViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.QueryingWithTextViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.SearchNotStartedViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.SearchResult
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import com.android.szparag.myextensionsbase.emptyString
import com.android.szparag.myextensionsbase.exhaustive
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.szparag.android.mypermissions.AndroidPermission.AccessFineLocationPermission
import com.szparag.android.mypermissions.PermissionCheckEvent
import io.reactivex.Single
import io.reactivex.rxkotlin.cast
import timber.log.Timber
import javax.inject.Inject
import kotlinx.android.synthetic.main.screen_select_departure_final.view.screen_select_departure_initial_recycler_airports as airportsRecycler
import kotlinx.android.synthetic.main.view_select_departure_input.view.view_select_departure_input_edit_text as inputEditText
import kotlinx.android.synthetic.main.view_select_departure_input.view.view_select_departure_input_gps_button as inputGpsButton

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
//todo: first letter in textview ALWAYS should be capitalized, even if input is from physical keyboard
//todo: or user explicitly tapped-off shift key
class FlighterSelectDepartureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMviConstraintLayout<SelectDepartureView, SelectDeparturePresenter, SelectDepartureViewState>(context, attrs, defStyleAttr),
    SelectDepartureView {

  companion object {
    val screenData by lazy {
      Screen(
          viewClass = FlighterSelectDepartureView::class.java,
          layoutResource = R.layout.screen_select_departure_initial
      )
    }
  }

  @Inject override lateinit var presenter: SelectDeparturePresenter
  private val airportsAdapter = AirportsAdapter()
  private var layoutAnimatedFlag = false


  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    airportsRecycler.adapter = airportsAdapter
    airportsRecycler.layoutManager = LinearLayoutManager(context)
  }

  override fun render(state: SelectDepartureViewState) {
    super.render(state)
    Timber.i("render, state: $state")
    when (state) {
      is SearchNotStartedViewState -> Unit
      is QueryingWithTextViewState -> {
        showAndAnimateRecyclerView()
      }
      is FetchingResultWithTextViewState -> {
        showAndAnimateRecyclerView()
      }
      is FetchingResultWithGpsViewState -> {
        showAndAnimateRecyclerView()
      }
      is EmptySearchResult -> Unit
      is SearchResult -> {
        showAndAnimateRecyclerView()
        airportsAdapter.update(state.results)
      }
      is AirportSelectedViewState -> {
        navigationDelegate.goToScreen(FlighterFlightsBrowserView.screenData)
      }
    }.exhaustive
  }

  //<editor-fold desc="Intent generation">
  override fun searchWithTextIntent() =
      RxView.focusChanges(inputEditText)
          .filter { it }
          .map { TextSearchIntent(emptyString()) }
          .switchMap { RxTextView.textChanges(inputEditText).map { TextSearchIntent(it.toString()) } }

  override fun searchWithGpsIntent() =
      RxView.clicks(inputGpsButton).map { GpsSearchIntent() }

  override fun airportSelectedIntent() =
      airportsAdapter.getItemClicks().map { airport -> AirportSelectionIntent(airport.airportIataCode, airport.airportName) }
  //</editor-fold>

  private fun showAndAnimateRecyclerView() {
    if (!layoutAnimatedFlag) {
      val constaintSet = ConstraintSet()
      constaintSet.clone(context, R.layout.screen_select_departure_final)
      val transition = ChangeBounds()
      transition.interpolator = DecelerateInterpolator(2f)
      transition.duration = 1000
      TransitionManager.beginDelayedTransition(this, transition)
      constaintSet.applyTo(this)
      layoutAnimatedFlag = true
    }
  }

  override fun requestGpsPermission(): Single<PermissionCheckEvent<AccessFineLocationPermission>> =
      permissionRequestAction.invoke(AccessFineLocationPermission).cast() //todo: cast? what if it fails?

  override fun instantiatePresenter() = Injector.get().inject(this)

  override fun getScreen() = screenData

}