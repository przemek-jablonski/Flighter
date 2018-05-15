package com.android.szparag.flighter.selectdeparture.views

import android.content.Context
import android.support.constraint.ConstraintSet
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.android.szparag.columbus.Screen
import com.android.szparag.flighter.R
import com.android.szparag.flighter.R.layout
import com.android.szparag.flighter.common.util.Injector
import com.android.szparag.flighter.selectdeparture.presenters.SelectDeparturePresenter
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureIntent.GpsSearchIntent
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureIntent.TextSearchIntent
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.EmptySearchResult
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.FetchingResultWithGpsViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.FetchingResultWithTextViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.QueryingWithTextViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.SearchNotStartedViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.SearchResult
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import com.android.szparag.myextensionsbase.emptyString
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_select_departure_input.view.view_select_departure_input_edit_text
import kotlinx.android.synthetic.main.view_select_departure_input.view.view_select_departure_input_gps_button
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
class FlighterSelectDepartureView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : BaseMviConstraintLayout<SelectDepartureViewState>(context, attrs, defStyleAttr), SelectDepartureView {

  override fun searchWithTextIntent(): Observable<TextSearchIntent> {
    return RxView.focusChanges(view_select_departure_input_edit_text)
        .filter { it }
        .map { TextSearchIntent(emptyString()) }
        .switchMap {
          RxTextView
              .textChanges(view_select_departure_input_edit_text)
              .map { TextSearchIntent(it.toString()) }
        }


//    return RxTextView.textChanges(view_select_departure_input_edit_text).map { TextSearchIntent(it.toString()) }
  }

  override fun searchWithGpsIntent(): Observable<GpsSearchIntent> {
    return RxView.clicks(view_select_departure_input_gps_button).map { GpsSearchIntent() }
  }

  companion object {
    val screenData by lazy {
      Screen(
          viewClass = FlighterSelectDepartureView::class.java,
          layoutResource = layout.screen_select_departure_initial
      )
    }
  }

  private var layoutMorphed = false

  override fun getScreen(): Screen = screenData

  @Inject
  lateinit var presenter: SelectDeparturePresenter //todo: this presenter should be overriden from parent class
  //todo: and methods instantiatePresenter, attachToPresenter and detachFromPresenter - removed

  init {
    Timber.d("init")
  }

  override fun render(state: SelectDepartureViewState) {
    super.render(state)
    Timber.i("render, state: $state")
    when (state) {
      is SearchNotStartedViewState -> {

      }
      is QueryingWithTextViewState -> {
        morphLayout()
      }
      is FetchingResultWithTextViewState -> {
        morphLayout()
      }
      is FetchingResultWithGpsViewState -> {
        morphLayout()
      }
      is EmptySearchResult -> {

      }
      is SearchResult -> {

      }
    }
  }

  private fun morphLayout() {
    if(!layoutMorphed) {
      val constaintSet = ConstraintSet()
      constaintSet.clone(context, R.layout.screen_select_departure_final)
      val transition = ChangeBounds()
      transition.interpolator = DecelerateInterpolator(2f)
      transition.duration = 1000
      TransitionManager.beginDelayedTransition(this, transition)
      constaintSet.applyTo(this)
      layoutMorphed = true
    }
  }

  override fun instantiatePresenter() {
    Timber.d("instantiatePresenter")
    Injector.get().inject(this)
  }

  override fun attachToPresenter() {
    Timber.d("attachToPresenter")
    presenter.attachView(this)
  }

  override fun detachFromPresenter() {
    Timber.d("detachFromPresenter")
    presenter.detachView(this)
  }

}