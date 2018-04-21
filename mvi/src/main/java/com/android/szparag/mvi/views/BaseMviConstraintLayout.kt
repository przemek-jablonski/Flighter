package com.android.szparag.mvi.views

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.CallSuper
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.android.szparag.mvi.navigator.MviNavigator
import com.android.szparag.myextensionsandroid.hide
import com.android.szparag.myextensionsandroid.show
import timber.log.Timber

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
abstract class BaseMviConstraintLayout<in VS : Any> @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), MviView<VS> {

  private var firstStateRendered = false
  lateinit var navigationDelegate: MviNavigator

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