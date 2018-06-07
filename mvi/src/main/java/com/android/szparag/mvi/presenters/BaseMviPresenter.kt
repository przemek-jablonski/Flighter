package com.android.szparag.mvi.presenters

import android.support.annotation.CallSuper
import com.android.szparag.mvi.models.MviInteractor
import com.android.szparag.mvi.views.MviView
import timber.log.Timber

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
abstract class BaseMviPresenter<V : MviView<VS>, I : MviInteractor<VS>, VS : Any> : MviPresenter<V, I, VS> {

  protected var view: V? = null
  protected lateinit var state: VS
  abstract val interactor: I
  private var viewAttachedFirstTime = true

  @CallSuper
  open fun onFirstViewAttached() {
    requireNotNull(view)
  }

  final override fun attachView(view: V) {
    this.view = view
    if(viewAttachedFirstTime) {
      viewAttachedFirstTime = false
      onFirstViewAttached()
    }
    onViewAttached(view)
  }

  final override fun detachView(view: V) {
    onViewDetached(view)
    this.view = null
  }

  @CallSuper
  protected open fun onViewAttached(view: V) {
  }

  @CallSuper
  protected open fun onViewDetached(view: V) {
  }

}