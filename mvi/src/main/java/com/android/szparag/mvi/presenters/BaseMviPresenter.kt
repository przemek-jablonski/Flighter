package com.android.szparag.mvi.presenters

import android.support.annotation.CallSuper
import com.android.szparag.mvi.interactors.MviInteractor
import com.android.szparag.mvi.models.ModelDistributor
import com.android.szparag.mvi.views.MviView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
abstract class BaseMviPresenter<V : MviView<VS>, VS : Any> : MviPresenter<V, VS> {

  protected var view: V? = null
  abstract val interactor: MviInteractor
  abstract val modelDistributor: ModelDistributor<VS>

  protected val presenterDisposables = CompositeDisposable()
  private var viewAttachedFirstTime = true


  /**
   * Attaches view to this presenter instance.
   * Any previous view reference stored in presenter is replaced with the one passed as a parameter.
   * Final, non-override'able class - in order to listen for attachment events, use onFirstViewAttached() and onViewAttached() callbacks.
   *
   * @see onFirstViewAttached
   * @see onViewAttached
   *
   * @param view view to attach to this presenter.
   */
  final override fun attachView(view: V) {
    Timber.i("attachView, view: $view")
    require(this.view == null, {
      Timber.e(
          "attachView, view already attached, this should not happen in usual circumstances, old view: ${this.view}, input view: $view")
    })
    this.view = view
    if (viewAttachedFirstTime) {
      viewAttachedFirstTime = false
      onFirstViewAttached()
    }
    onViewAttached(view)
  }

  /**
   * Detaches view from this presenter instance.
   * Detachment is implemented with nulling-out already existing reference to the view. If view passed to this method is different that the one
   * stored in reference in this presenter, then IllegalArgumentException will be thrown.
   * Final, non-override'able class - in order to listen for attachment events, use onViewDetached() callback.
   *
   * @see onViewDetached
   *
   * @param view view to detach from this presenter.
   */
  final override fun detachView(view: V) {
    Timber.i("detachView, view: $view")
    require(this.view == view, {
      Timber.e(
          "detachView, trying to detach different view than already stored in the presenter, attached view: ${this.view}, input view: $view")
    })
    onViewDetached(view)
    this.view = null
  }

  /**
   * Callback informs that first-ever view was attached to this presenter.
   * Useful for assigning first ViewState or performing some other logic when view/screen has been inflated for the first time during application lifecycle.
   * Reattaching view due to moving forward/backwards in stack or due to device rotation WILL NOT trigger this callback.
   *
   * Overriding methods should call this code using super.onFirstViewAttached() otherwise risking presenter logic being unstable.
   *
   * @see view
   * @see VS
   */
  @CallSuper
  open fun onFirstViewAttached() {
    Timber.i("onFirstViewAttached")
    requireNotNull(view) //todo: is it nessesary?
  }

  /**
   * Informs that view was attached to this presenter. Instance of attached view may be obtained from the presenter field or this callback parameter.
   * Reattaching view due to moving forward/backwards in stack or due to device rotation WILL trigger this callback every time.
   *
   * Overriding methods should call this code using super.onViewAttached() otherwise risking presenter logic being unstable.
   *
   * This method handles calls to view.render() from stream of models from the modelDistributor, so there is no need to call view.render() from extended child classes of this BaseMviPresenter.
   *
   * @see modelDistributor
   * @see view
   * @see V
   *
   * @param view attached view
   */
  @CallSuper
  protected open fun onViewAttached(view: V) {
    Timber.i("onViewAttached, view: $view")
    presenterDisposables.clear()
    presenterDisposables.add(
        modelDistributor
            .getModels()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState ->
              Timber.i("modelDistributor.getModels(), viewState: $viewState, view: $view")
              view.render(viewState)
            }
    )
  }

  /**
   *
   * Informs that view was detached to this presenter.
   * Re-detaching view due to moving forward/backwards in stack or due to device rotation WILL trigger this callback every time.
   * This callback is synchronous, so operations on this soon-to-be detached view are possible although in limited time capacity.
   *
   * Overriding methods should call this code using super.onViewDetached() otherwise risking presenter logic being unstable.
   *
   * This method clears all disposables which were thrown into presenterDisposable during screen/view lifecycle.
   *
   * @see presenterDisposables
   * @param view detached view
   */
  @CallSuper
  protected open fun onViewDetached(view: V) {
    Timber.i("onViewDetached, view: $view")
    presenterDisposables.clear()
  }

  //todo: check if this covariant shit will break flighters extended classes
//  override fun addIntentProcessing(intentProcessing: Observable<VS>) {
//    presenterDisposables.add(intentProcessing.subscribe { viewState -> modelDistributor.replaceModel(viewState) })
//  }

}