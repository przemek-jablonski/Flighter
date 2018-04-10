package com.android.szparag.mvi.presenters

import android.support.annotation.CallSuper
import com.android.szparag.mvi.models.MviModel
import com.android.szparag.mvi.views.MviView
import timber.log.Timber

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
abstract class BaseMviPresenter<V : MviView<VS>, M : MviModel<VS>, VS : Any> : MviPresenter<V, M, VS> {

  protected var view: V? = null
  protected lateinit var state: VS
  protected open lateinit var model: M //todo: shouldn't this be called interactor
  private var viewAttachedFirstTime = true

  init {
    Timber.d("init")
  }

  abstract fun onFirstViewAttached()

  final override fun attachView(view: V) {
    Timber.d("attachView, view: $view, viewAttachedFirstTime: $viewAttachedFirstTime")
    this.view = view
    if(viewAttachedFirstTime) {
      viewAttachedFirstTime = false
      onFirstViewAttached()
    }
    onViewAttached(view)
  }

  final override fun detachView(view: V) {
    Timber.d("detachView, view: $view")
    this.view = null
  }

  @CallSuper
  protected open fun onViewAttached(view: V) {
    Timber.d("onViewAttached, view: $view")
  }

  @CallSuper
  protected open fun onViewDetached(view: V) {
    Timber.d("onViewDetached, view: $view")
  }

}