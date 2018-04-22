package com.android.szparag.flighter.selectdeparture.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.android.szparag.flighter.R.layout
import com.android.szparag.flighter.common.util.Injector
import com.android.szparag.flighter.selectdeparture.presenters.SelectDeparturePresenter
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState.SearchNotStartedViewState
import com.android.szparag.mvi.navigator.Screen
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
class FlighterSelectDepartureView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : BaseMviConstraintLayout<SelectDepartureViewState>(context, attrs, defStyleAttr), SelectDepartureView {

  companion object {
    val screenData by lazy {
      Screen(
          viewClass = FlighterSelectDepartureView::class.java,
          layoutResource = layout.screen_select_departure
      )
    }
  }

  @Inject
  lateinit var presenter: SelectDeparturePresenter

  init {
    Timber.d("init")
    Injector.get().inject(this)
  }

  override fun render(state: SelectDepartureViewState) {
    super.render(state)
    Timber.d("render, state: $state")
    when (state) {
      is SearchNotStartedViewState -> {

      }
    }
  }

  override fun instantiatePresenter() {
    Timber.d("instantiatePresenter")
  }

  override fun attachToPresenter() {
    Timber.d("attachToPresenter")
  }

  override fun detachFromPresenter() {
    Timber.d("detachFromPresenter")
    presenter
  }

}