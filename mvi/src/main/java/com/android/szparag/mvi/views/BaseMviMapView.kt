package com.android.szparag.mvi.views

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.CallSuper
import android.util.AttributeSet
import com.android.szparag.mvi.navigator.Navigator
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
  override lateinit var navigationDelegate: Navigator

  init {
    Timber.d("init")
    if(!isInEditMode) hide()
  }

  abstract fun instantiatePresenter()

  abstract fun attachToPresenter()

  abstract fun detachFromPresenter()

  @CallSuper
  override fun render(state: VS) {
    if (!firstStateRendered) { handleFirstRender(state) }
  }

  protected open fun handleFirstRender(state: VS) {
    show()
    firstStateRendered = true
  }

  @SuppressLint("MissingSuperCall")
  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    if (isInEditMode) return
    instantiatePresenter()
    attachToPresenter()
  }

  @SuppressLint("MissingSuperCall")
  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    if (isInEditMode) return
    detachFromPresenter()
  }

}