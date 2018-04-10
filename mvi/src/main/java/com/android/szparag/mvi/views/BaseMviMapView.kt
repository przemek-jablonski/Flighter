package com.android.szparag.mvi.views

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.CallSuper
import android.util.AttributeSet
import com.android.szparag.myextensionsandroid.hide
import com.android.szparag.myextensionsandroid.show
import com.google.android.gms.maps.MapView
import timber.log.Timber

//todo: extract that as a separate module, as it requires heavy gradle dependency (GooglePlayServices-maps)
//todo: remove timber, add static logger with a static switch
//todo: this has EXACT SAME code as BaseMviConstraintLayout, deal with this
abstract class BaseMviMapView<in VS: Any> @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr), MviView<VS> {

  private var firstStateRendered = false

  init {
    Timber.d("init")
    hide()
  }

  abstract fun instantiatePresenter()

  abstract fun attachToPresenter()

  abstract fun detachFromPresenter()

  @CallSuper
  override fun render(state: VS) {
    Timber.d("render, state: $state")
    if (!firstStateRendered) { handleFirstRender(state) }
  }

  protected open fun handleFirstRender(state: VS) {
    Timber.d("handleFirstRender, state: $state")
    show()
    firstStateRendered = true
  }

  @SuppressLint("MissingSuperCall")
  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    Timber.d("onAttachedToWindow")
    instantiatePresenter()
    attachToPresenter()
  }

  @SuppressLint("MissingSuperCall")
  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    Timber.d("onDetachedFromWindow")
    detachFromPresenter()
  }

}