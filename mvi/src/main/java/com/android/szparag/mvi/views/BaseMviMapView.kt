package com.android.szparag.mvi.views

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.CallSuper
import android.util.AttributeSet
import com.android.szparag.columbus.Navigator
import com.android.szparag.mvi.presenters.MviPresenter
import com.android.szparag.myextensionsandroid.hide
import com.android.szparag.myextensionsandroid.show
import com.google.android.gms.maps.MapView
import com.szparag.android.mypermissions.PermissionRequestAction
import timber.log.Timber

//todo: extract that as a separate module, as it requires heavy gradle dependency (GooglePlayServices-maps)
//todo: remove timber, add static logger with a static switch
//todo: this has EXACT SAME code as BaseMviConstraintLayout, deal with this
abstract class BaseMviMapView<in V : MviView<VS>, P : MviPresenter<V, VS>, VS : Any> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr), MviView<VS> {

  private var firstStateRendered = false
  override lateinit var navigationDelegate: Navigator
  override lateinit var permissionRequestAction: PermissionRequestAction
  abstract var presenter: P

  init {
    Timber.d("init")
    if (!isInEditMode) hide()
  }

  abstract fun instantiatePresenter()

  @Suppress("UNCHECKED_CAST")
  @CallSuper
  open fun attachToPresenter() {
    presenter.attachView(this as V)
  }

  @Suppress("UNCHECKED_CAST")
  @CallSuper
  open fun detachFromPresenter() {
    presenter.detachView(this as V)
  }

  @CallSuper
  override fun render(state: VS) {
    if (!firstStateRendered) {
      handleFirstRender(state)
    }
  }

  protected open fun handleFirstRender(state: VS) {
    show()
    firstStateRendered = true
  }

  @SuppressLint("MissingSuperCall")
  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    if (isInEditMode) return
    instantiatePresenter()
    attachToPresenter()
  }

  @SuppressLint("MissingSuperCall")
  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    if (isInEditMode) return
    detachFromPresenter()
  }

}