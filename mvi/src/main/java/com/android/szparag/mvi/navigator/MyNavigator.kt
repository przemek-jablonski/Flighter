package com.android.szparag.mvi.navigator

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.szparag.mvi.navigator.NavigationLayer.BACKGROUND
import com.android.szparag.mvi.navigator.NavigationLayer.DIALOGS
import com.android.szparag.mvi.navigator.NavigationLayer.FOREGROUND
import com.android.szparag.mvi.navigator.NavigationTransitionOutPolicy.PERSISTENT_IN_STACK
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import com.android.szparag.mvi.views.MviView
import com.android.szparag.myextensionsandroid.addView
import com.android.szparag.myextensionsandroid.asShortString
import com.android.szparag.myextensionsandroid.childByClass
import com.android.szparag.myextensionsandroid.idAsString
import com.android.szparag.myextensionsandroid.indexOfChildByClass
import timber.log.Timber

class MyNavigator(
    protected val globalContainer: ViewGroup,
    protected val inflater: LayoutInflater,
    private val onlyOneDialogLimit: Boolean = true
) : Navigator {


  private val dialogStack = NavigationStack()
  private val foregroundStack = NavigationStack()
  private val backgroundStack = NavigationStack()

  init {
    Timber.d("init")
    dialogStack.onScreenPushedListener = this::onDialogPushed
    dialogStack.onScreenPoppedListener = this::onDialogPopped
    foregroundStack.onScreenPushedListener = this::onForegroundPushed
    foregroundStack.onScreenPoppedListener = this::onForegroundPopped
    backgroundStack.onScreenPushedListener = this::onBackgroundPushed
    backgroundStack.onScreenPoppedListener = this::onBackgroundPopped
  }


  override fun goToScreen(screen: Screen) {
    Timber.d("goToScreen, screen: $screen")
    when (screen.layer) {
      DIALOGS -> dialogStack.push(screen)
      FOREGROUND -> foregroundStack.push(screen)
      BACKGROUND -> backgroundStack.push(screen)
    }
  }


  override fun handleBackPress() {
    Timber.d("handleBackPress")
    if (dialogStack.isNotEmpty())
      dialogStack.pop()
    else
      foregroundStack.pop()
  }

  private fun onDialogPushed(screen: Screen) {
    Timber.d("onDialogPushed, screen: $screen")
    handleOutgoingScreen(dialogStack.peekPrevious())
    handleIncomingScreen(dialogStack.peekCurrent())
  }

  private fun onDialogPopped(screen: Screen?) {
    Timber.d("onDialogPopped, screen: $screen")
    handleOutgoingScreen(dialogStack.peekPrevious())
    handleIncomingScreen(dialogStack.peekCurrent())
  }

  private fun onForegroundPushed(screen: Screen) {
    Timber.d("onForegroundPushed, screen: $screen")
    handleOutgoingScreen(foregroundStack.peekPrevious())
    handleIncomingScreen(foregroundStack.peekCurrent())
  }

  private fun onForegroundPopped(screen: Screen?) {
    Timber.d("onForegroundPopped, screen: $screen")
    handleOutgoingScreen(foregroundStack.peekCurrent())
    handleIncomingScreen(foregroundStack.peekPrevious())
  }

  private fun onBackgroundPushed(screen: Screen) {
    Timber.d("onBackgroundPushed, screen: $screen")
    handleOutgoingScreen(backgroundStack.peekPrevious())
    handleIncomingScreen(backgroundStack.peekCurrent())
  }

  private fun onBackgroundPopped(screen: Screen?) {
    Timber.d("onBackgroundPopped, screen: $screen")
    handleOutgoingScreen(backgroundStack.peekCurrent())
    handleIncomingScreen(backgroundStack.peekPrevious())
  }


  private fun handleIncomingScreen(screen: Screen?) {
    Timber.d("handleIncomingScreen, screen: $screen")
    screen?.let {
      inflateScreen(inflater, globalContainer, screen.layoutResource).apply {
        constructScreenAnimation(this, screen.transitionInAnimation)
        this as MviView<*>
        this.navigationDelegate = this@MyNavigator
        globalContainer.addView(this)
        //todo: start anim
      }
    }
  }

  private fun handleOutgoingScreen(screen: Screen?) {
    Timber.d("handleOutgoingScreen, screen: $screen")
    screen?.let {
      globalContainer.childByClass(screen.viewClass)?.let { view ->
        constructScreenAnimation(view, screen.transitionOutAnimation)
        globalContainer.removeViewInLayout(view)
      }
      //todo: start anim and kill view in onEnd callback
    }
  }

  private fun inflateScreen(inflater: LayoutInflater, container: ViewGroup, @LayoutRes screenLayoutResource: LayoutId): View {
    Timber.d("inflateScreen, inflater: $inflater, screenLayoutResource: $screenLayoutResource")
    return inflater.inflate(screenLayoutResource, container, false)
  }

  private fun constructScreenAnimation(target: View, animation: NavigationTransitionAnimation) {
    Timber.d("constructScreenAnimation, target: $target, animation: $animation")
    when (animation) {
      is NavigationTransitionInAnimation.FADE_IN -> {}
      is NavigationTransitionInAnimation.MOVE_IN -> {}
      is NavigationTransitionOutAnimation.FADE_OUT -> {}
      is NavigationTransitionOutAnimation.MOVE_OUT -> {}
      is NavigationTransitionAnimation.INSTANT -> {}
    }
  }

}