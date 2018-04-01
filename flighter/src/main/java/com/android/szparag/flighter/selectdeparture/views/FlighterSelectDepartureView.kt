package com.android.szparag.flighter.selectdeparture.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.android.szparag.flighter.common.util.Injector
import com.android.szparag.flighter.selectdeparture.presenters.SelectDeparturePresenter
import com.android.szparag.flighter.selectdeparture.states.SelectDepartureViewState
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
class FlighterSelectDepartureView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr), SelectDepartureView {

  @Inject
  lateinit var presenter: SelectDeparturePresenter

  init {
    Timber.d("init")
    Injector.get().inject(this)
  }

  override fun render(state: SelectDepartureViewState) {
    Timber.d("render, state: $state")
    when (state) {
      is SelectDepartureViewState.SearchNotStartedViewState -> {

      }
    }
  }

}