package com.android.szparag.mvi.presenters

import com.android.szparag.mvi.views.MviView
import timber.log.Timber

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
abstract class BaseMviPresenter<in V : MviView<VS>, in VS : Any> : MviPresenter<V, VS> {

  private var view: V? = null

  init {
    Timber.d("init")
  }

  final override fun attachView(view: V) {
    Timber.d("attachView, view: $view")
  }

  final override fun detachView(view: V) {
    Timber.d("detachView, view: $view")
  }

  override fun onViewAttached(view: V) {
    Timber.d("onViewAttached, view: $view")
  }

  override fun onViewDetached(view: V) {
    Timber.d("onViewDetached, view: $view")
  }
}