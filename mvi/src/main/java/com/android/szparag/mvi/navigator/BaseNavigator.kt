package com.android.szparag.mvi.navigator

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.android.szparag.mvi.navigator.NavigationLayer.FOREGROUND
import com.android.szparag.mvi.navigator.NavigationTransitionInPolicy.KILL_ALL_PREVIOUS
import com.android.szparag.mvi.navigator.NavigationTransitionInPolicy.KILL_PREVIOUS
import timber.log.Timber
import java.util.Stack

abstract class BaseNavigator<T : Screen>(
    protected var globalContainer: FrameLayout,
    protected val inflater: LayoutInflater
) : Navigator<T> {

  protected val navigationStackDialogs = Stack<T>()
  protected val navigationStackForeground = Stack<T>()
  protected val navigationStackBackground = Stack<T>()

  override fun showScreen(screen: T) {
    Timber.d("showScreen, screen: $screen")
    val navigationStack = if (screen.layer == FOREGROUND) navigationStackForeground else navigationStackBackground
    when (screen.transitionInPolicy) {
      is KILL_PREVIOUS -> {
        killScreens(navigationStack, screen.transitionInPolicy.itemCount, true)
        showScreen(navigationStack, globalContainer, screen)
      }
      is KILL_ALL_PREVIOUS -> {
        killScreens(navigationStack, navigationStackForeground.count(), false)
        showScreen(navigationStack, globalContainer, screen)
      }
      is NavigationTransitionInPolicy.DEFAULT_ADD_TO_STACK -> {
        showScreen(navigationStack, globalContainer, screen)
      }
    }
  }

  private fun killScreens(targetStack: Stack<T>, count: Int, omitTopScreen: Boolean = true) {
    Timber.d("killScreens, targetStack: $targetStack, count: $count, omitTopScreen: $omitTopScreen")

  }

  private fun showScreen(targetStack: Stack<T>, container: ViewGroup, screen: T) {
    Timber.d("addScreen, targetStack: $targetStack, container: $container, screen: $screen")
    globalContainer.addView(inflater.inflate(screen.layoutResource, container, false))
    targetStack.add(screen)
  }

  private fun removeScreen(targetStack: Stack<T>, container: ViewGroup, screen: T) {
    Timber.d("removeScreen, targetStack: $targetStack, container: $container, screen: $screen")
    globalContainer.removeViewAt(globalContainer.childCount.dec())
  }

  override fun handleBackPress() {
    Timber.d("handleBackPress")
    navigationStackForeground.pop()
    showScreen(navigationStackForeground.peek())
  }

}