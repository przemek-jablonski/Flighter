package com.android.szparag.mvi.navigator

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.szparag.mvi.navigator.NavigationLayer.*
import com.android.szparag.mvi.navigator.NavigationTransitionOutPolicy.PERSISTENT_IN_STACK
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import com.android.szparag.myextensionsandroid.addView
import com.android.szparag.myextensionsandroid.asShortString
import com.android.szparag.myextensionsandroid.idAsString
import com.android.szparag.myextensionsandroid.indexOfChildByClass
import timber.log.Timber

abstract class BaseMviNavigator(
    protected val globalContainer: ViewGroup,
    protected val inflater: LayoutInflater,
    private val onlyOneDialogLimit: Boolean = true
) : MviNavigator {

  private val dialogStack = NavigationStack()
  private val foregroundStack = NavigationStack()
  private val backgroundStack = NavigationStack()

  init {
    Timber.i("init")
    dialogStack.onScreenPushedListener = this::onDialogPushed
    dialogStack.onScreenPoppedListener = this::onDialogPopped
    foregroundStack.onScreenPushedListener = this::onForegroundPushed
    foregroundStack.onScreenPoppedListener = this::onForegroundPopped
    backgroundStack.onScreenPushedListener = this::onBackgroundPushed
    backgroundStack.onScreenPoppedListener = this::onBackgroundPopped
  }


  fun goToScreen(screen: Screen) {
    Timber.i("goToScreen, screen: $screen")
    when(screen.layer) {
      DIALOGS -> dialogStack.push(screen)
      FOREGROUND -> foregroundStack.push(screen)
      BACKGROUND -> backgroundStack.push(screen)
    }
  }


  override fun handleBackPress() {
    Timber.i("handleBackPress")
    if (dialogStack.isNotEmpty())
      dialogStack.pop()
    else
      foregroundStack.pop()
  }

  private fun onDialogPushed(screen: Screen) {
    Timber.i("onDialogPushed, screen: $screen")
    handleOutgoingScreen(dialogStack.peekPrevious())
    handleIncomingScreen(dialogStack.peekCurrent())
  }

  private fun onDialogPopped(screen: Screen?) {
    Timber.i("onDialogPopped, screen: $screen")
    handleOutgoingScreen(dialogStack.peekPrevious())
    handleIncomingScreen(dialogStack.peekCurrent())
  }

  private fun onForegroundPushed(screen: Screen) {
    Timber.i("onForegroundPushed, screen: $screen")
    handleOutgoingScreen(foregroundStack.peekPrevious())
    handleIncomingScreen(foregroundStack.peekCurrent())
  }

  private fun onForegroundPopped(screen: Screen?) {
    Timber.i("onForegroundPopped, screen: $screen")
    handleOutgoingScreen(foregroundStack.peekCurrent())
    handleIncomingScreen(foregroundStack.peekPrevious())
  }

  private fun onBackgroundPushed(screen: Screen) {
    Timber.i("onBackgroundPushed, screen: $screen")
    handleOutgoingScreen(backgroundStack.peekPrevious())
    handleIncomingScreen(backgroundStack.peekCurrent())
  }

  private fun onBackgroundPopped(screen: Screen?) {
    Timber.i("onBackgroundPopped, screen: $screen")
    handleOutgoingScreen(backgroundStack.peekCurrent())
    handleIncomingScreen(backgroundStack.peekPrevious())
  }


  private fun handleIncomingScreen(screen: Screen) {
    inflateScreen(inflater, globalContainer, screen.layoutResource).apply {
      constructScreenAnimation(this, screen.transitionInAnimation)
      this as BaseMviConstraintLayout<*> //todo: there may be BaseMviMapView as well. Extract to interface common stuff (like navigation delegate)
      globalContainer.addView(this)
    }
  }

  private fun handleOutgoingScreen() {

  }

  private fun inflateScreen(inflater: LayoutInflater, container: ViewGroup, @LayoutRes screenLayoutResource: LayoutId): View {
    Timber.i("inflateScreen, inflater: $inflater, screenLayoutResource: $screenLayoutResource")
    return inflater.inflate(screenLayoutResource, container, false)
  }

  private fun constructScreenAnimation(target: View, animation: NavigationTransitionAnimation) {
    Timber.i("constructScreenAnimation, target: $target, animation: $animation")
    when(animation) {
      is NavigationTransitionInAnimation.FADE_IN -> TODO()
      is NavigationTransitionInAnimation.MOVE_IN -> TODO()
      is NavigationTransitionOutAnimation.FADE_OUT -> TODO()
      is NavigationTransitionOutAnimation.MOVE_OUT -> TODO()
      is NavigationTransitionAnimation.INSTANT -> TODO()
    }

  }

  //________________________________________________________________________________


  private fun handleScreenTransitionOut(container: ViewGroup, screen: Screen?) {
    Timber.i("handleScreenTransitionOut, container: ${container.asShortString()}, screen: $screen")
    screen?.let {
      if(it.transitionOutPolicy != PERSISTENT_IN_STACK()) {
        removeScreenFromContainer(container, it.viewClass)
      }
    }
  }

  private fun handleScreenTransitionIn(container: ViewGroup, screen: Screen) {
    Timber.i("handleScreenTransitionIn,  container: ${container.asShortString()}, screen: $screen")
    showScreenInContainer(container, screen.layoutResource)
  }


  private fun showScreenInContainer(container: ViewGroup, @LayoutRes screenLayoutResource: LayoutId) {
    Timber.i("showScreenInContainer, container: ${container.asShortString()}, screenLayoutResource: ${screenLayoutResource.idAsString(globalContainer.resources)}")
    container.addView(inflater, screenLayoutResource, {
      it as BaseMviConstraintLayout<*>
      it.navigationDelegate = this
    })
  }

  private fun removeScreenFromContainer(container: ViewGroup, screenViewClass: Class<*>) {
    Timber.i("removeScreenFromContainer, container: ${container.asShortString()}, screenViewClass: $screenViewClass")
    container.removeViewAt(container.indexOfChildByClass(screenViewClass))
  }

}