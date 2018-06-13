package com.android.szparag.mvi.presenters

import android.support.annotation.CallSuper
import com.android.szparag.mvi.interactors.MviInteractor
import com.android.szparag.mvi.models.ModelRepository
import com.android.szparag.mvi.util.addTo
import com.android.szparag.mvi.views.MviView
import io.reactivex.Observable
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
  abstract val modelRepository: ModelRepository<VS>

  protected val presenterDisposables = CompositeDisposable()
  private var viewAttachedFirstTime = true

  /**
   * Defines what ViewState should be created for given View when attaching to this presenter for the first time.
   * Called in onFirstViewAttached() callback.
   *
   * If you do not wish to assign first ViewState upon attaching view for the first time, return null with this method
   *
   * @see onFirstViewAttached
   */
  abstract fun distributeFirstViewState(): VS?

  /**
   * Defines action processing which should take place upon receiving any userIntent from the view.
   * Ideally, all of the Intents defined for view of this presenter should be covered with some processing.
   *
   * Use registerIntentProcessing() method for adding processing to gain auto-dispose and auto-rendering feature for free.
   *
   * @see registerIntentProcessing
   * @see onViewAttached
   * @param view currently attached view to this presenter
   */
  abstract fun processUserIntents(view: V)


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
    onViewAttached(view)
    if (viewAttachedFirstTime) {
      viewAttachedFirstTime = false
      onFirstViewAttached()
    }
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
    Timber.i("onFirstViewAttached, first viewState: ${distributeFirstViewState()}, view: $view")
    requireNotNull(view) //todo: is it nessesary?
    distributeFirstViewState()?.let { viewState -> modelRepository.replaceModel(viewState) }
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
    processUserIntents(view)
    modelRepository
        .getModels()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { viewState ->
          Timber.i("modelDistributor.getModels(), viewState: $viewState, view: $view")
          view.render(viewState)
        }
        .addTo(presenterDisposables)
  }

  /**
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

  /**
   * Utility method for automatic observable disposal (with presenterDisposables field).
   * Adds predefined subscribe operator code to the chain so that all viewStates are automatically distributed to modelDistributor.
   *
   * Designed to use with chain that converts user intents to view states.
   *
   * @see presenterDisposables
   * @param intentProcessing processing that has been applied to given Intent
   */
  override fun registerIntentProcessing(intentProcessing: Observable<out VS>) {
    intentProcessing.subscribe { viewState -> modelRepository.replaceModel(viewState) }.addTo(presenterDisposables)
  }

  /**
   * Extension to registerIntentProcessing method.
   *
   * @see registerIntentProcessing
   */
  fun Observable<out VS>.registerProcessing(presenter: BaseMviPresenter<V, VS>) =
      presenter.registerIntentProcessing(this)

}