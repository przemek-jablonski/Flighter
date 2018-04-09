package com.android.szparag.mvi.presenters

import com.android.szparag.mvi.models.MviModel
import com.android.szparag.mvi.views.MviView
import timber.log.Timber

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
abstract class BaseMviPresenter<V : MviView<VS>, M : MviModel<VS>, VS : Any> : MviPresenter<V, M, VS> {

  protected var view: V? = null
  protected lateinit var state: VS
  protected lateinit var model: M
  private var viewAttachedFirstTime = true

  init {
    Timber.d("[${hashCode()}]: init")
  }

  abstract fun onFirstViewAttached()

  final override fun attachView(view: V) {
    Timber.d("[${hashCode()}]: attachView, view: $view")
    if(viewAttachedFirstTime) {
      viewAttachedFirstTime = false
      onFirstViewAttached()
    }
  }

  final override fun detachView(view: V) {
    Timber.d("[${hashCode()}]: detachView, view: $view")
  }

  override fun onViewAttached(view: V) {
    Timber.d("[${hashCode()}]: onViewAttached, view: $view")
  }

  override fun onViewDetached(view: V) {
    Timber.d("[${hashCode()}]: onViewDetached, view: $view")
  }

}