package com.android.szparag.mvi.navigator

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.szparag.mvi.navigator.NavigationTransitionOutPolicy.PERSISTENT_IN_STACK
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import com.android.szparag.myextensionsandroid.addView
import com.android.szparag.myextensionsandroid.asShortString
import com.android.szparag.myextensionsandroid.idAsString
import com.android.szparag.myextensionsandroid.indexOfChildByClass
import timber.log.Timber

abstract class BaseMviNavigator(
    protected val globalContainer: ViewGroup,
    protected val inflater: LayoutInflater
) : MviNavigator {

  protected val stack = NavigationStack()

  override fun showScreen(screen: Screen) {
    Timber.i("showScreen, screen: $screen, stack: $stack")
    handleTransitionOut(globalContainer, stack.pop())
    handleTransitionIn(globalContainer, screen)
    stack.push(screen)
    Timber.i("showScreen, screen: $screen, stack: $stack")
  }

  private fun handleTransitionOut(container: ViewGroup, screen: Screen?) {
    Timber.i("handleTransitionOut, container: ${container.asShortString()}, screen: $screen")
    screen?.let {
      if(it.transitionOutPolicy != PERSISTENT_IN_STACK()) {
        removeScreen(container, it.viewClass)
      }
    }
  }

  private fun handleTransitionIn(container: ViewGroup, screen: Screen) {
    Timber.i("handleTransitionIn,  container: ${container.asShortString()}, screen: $screen")
    showScreen(container, screen.layoutResource)
  }


  private fun showScreen(container: ViewGroup, @LayoutRes screenLayoutResource: LayoutId) {
    Timber.i("showScreen, container: ${container.asShortString()}, screenLayoutResource: ${screenLayoutResource.idAsString(globalContainer.resources)}")
    container.addView(inflater, screenLayoutResource, {
      it as BaseMviConstraintLayout<*>
      it.navigationDelegate = this
    })
  }

  private fun removeScreen(container: ViewGroup, screenViewClass: Class<*>) {
    Timber.i("removeScreen, container: ${container.asShortString()}, screenViewClass: $screenViewClass")
    container.removeViewAt(container.indexOfChildByClass(screenViewClass))
  }


  override fun handleBackPress() {
    Timber.i("handleBackPress")
    handleTransitionOut(globalContainer, stack.pop())
    handleTransitionIn(globalContainer, stack.peek())
  }

}