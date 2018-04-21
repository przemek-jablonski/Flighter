package com.android.szparag.mvi.navigator

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.szparag.mvi.navigator.NavigationTransitionOutPolicy.PERSISTENT_IN_STACK
import com.android.szparag.mvi.views.BaseMviConstraintLayout
import com.android.szparag.myextensionsandroid.addView
import com.android.szparag.myextensionsandroid.getChildren
import kotlinx.android.synthetic.clearFindViewByIdCache
import timber.log.Timber

abstract class BaseMviNavigator(
    protected val globalContainer: ViewGroup,
    protected val inflater: LayoutInflater
) : MviNavigator {

  protected val stack = NavigationStack()

  override fun showScreen(screen: Screen) {
    Timber.d("showScreen, screen: $screen, stack: $stack")
    handleTransitionOut(stack.pop())
    handleTransitionIn(globalContainer, screen)
    stack.push(screen)
    Timber.d("showScreen, screen: $screen, stack: $stack")
  }

  private fun handleTransitionOut(targetScreen: Screen?) {
    targetScreen?.let { screen ->
      if(screen.transitionOutPolicy != PERSISTENT_IN_STACK()) {

      }
    }
  }

  private fun handleTransitionIn(container: ViewGroup, screen: Screen) {
    Timber.d("handleTransitionIn,  container: $container, screen: $screen")
    showScreen(container, screen.layoutResource)
    Timber.d("handleTransitionIn, container: $container, screen: $screen")
  }


  private fun showScreen(container: ViewGroup, @LayoutRes screenLayoutResource: LayoutId) {
    Timber.d("showScreen, container: $container, screenLayoutResource: $screenLayoutResource")
    container.addView(inflater, screenLayoutResource, {
      if (it is BaseMviConstraintLayout<*>) {
        it.navigationDelegate = this
      }
    })
  }

  private fun removeScreen(container: ViewGroup, @LayoutRes screenLayoutResource: LayoutId) {
//    container.getChildren().forEach { if (it.clearFindViewByIdCache()) }
  }


  override fun handleBackPress() {
    Timber.d("handleBackPress")
//    removeScreen(navigationStackForeground, )
//    navigationStackForeground.pop()
//    showScreen(navigationStackForeground.peek())
  }

}