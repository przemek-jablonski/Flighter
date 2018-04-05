package com.android.szparag.mvi.presenters

import com.android.szparag.mvi.views.MviView
import timber.log.Timber

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
abstract class BaseMviPresenter<in V : MviView<VS>, in VS : Any> : MviPresenter<V, VS> {

  private var view: V? = null

  init {
    Timber.d("[${hashCode()}]: init")
  }

  final override fun attachView(view: V) {
    Timber.d("[${hashCode()}]: attachView, view: $view")
  }

  final override fun detachView(view: V) {
    Timber.d("[${hashCode()}]: detachView, view: $view")
  }

  abstract override fun onViewAttached(view: V)

  abstract override fun onViewDetached(view: V)
}