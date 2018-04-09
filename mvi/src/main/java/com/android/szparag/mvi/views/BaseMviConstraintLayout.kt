package com.android.szparag.mvi.views

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.CallSuper
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
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

  init {
    Timber.d("[${hashCode()}]: init")
    hide()
  }

  abstract fun instantiatePresenter()

  abstract fun attachToPresenter()

  abstract fun detachFromPresenter()

  @CallSuper
  override fun render(state: VS) {
    Timber.d("[${hashCode()}]: render, state: $state")
    if (!firstStateRendered) { handleFirstRender(state) }
  }

  protected open fun handleFirstRender(state: VS) {
    Timber.d("[${hashCode()}]: handleFirstRender, state: $state")
    show()
    firstStateRendered = true
  }

  @SuppressLint("MissingSuperCall")
  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    Timber.d("[${hashCode()}]: onAttachedToWindow")
    instantiatePresenter()
    attachToPresenter()
  }

  @SuppressLint("MissingSuperCall")
  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    Timber.d("[${hashCode()}]: onDetachedFromWindow")
    detachFromPresenter()
  }
}