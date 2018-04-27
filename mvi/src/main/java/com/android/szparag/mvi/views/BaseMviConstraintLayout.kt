package com.android.szparag.mvi.views

import android.content.Context
import android.support.annotation.CallSuper
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.android.szparag.mvi.navigator.Navigator
import com.android.szparag.myextensionsandroid.hide
import com.android.szparag.myextensionsandroid.show

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
abstract class BaseMviConstraintLayout<in VS : Any> @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), MviView<VS> {

  private var firstStateRendered = false
  override lateinit var navigationDelegate: Navigator

  init {
    if (!isInEditMode) hide()
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

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    if (isInEditMode) return
    instantiatePresenter()
    attachToPresenter()
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    if (isInEditMode) return
    detachFromPresenter()
  }

}