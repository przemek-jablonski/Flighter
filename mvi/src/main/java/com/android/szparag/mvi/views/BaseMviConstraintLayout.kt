package com.android.szparag.mvi.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import timber.log.Timber

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
abstract class BaseMviConstraintLayout<in VS : Any> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr), MviView<VS> {

  init {
    Timber.d("init")
  }

  abstract override fun attachToPresenter()

  abstract override fun detachFromPresenter()

  override fun onAttachedToWindow() {
    Timber.d("onAttachedToWindow")
    super.onAttachedToWindow()
    instantiatePresenter()
    attachToPresenter()
  }

  override fun onDetachedFromWindow() {
    Timber.d("onDetachedFromWindow")
    super.onDetachedFromWindow()
    detachFromPresenter()
  }

}