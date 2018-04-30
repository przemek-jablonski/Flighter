package com.android.szparag.mvi.navigator

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewPropertyAnimator
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.android.szparag.mvi.navigator.NavigationLayer.*
import com.android.szparag.mvi.navigator.NavigationTransitionInPolicy.*
import com.android.szparag.mvi.views.MviView
import com.android.szparag.myextensionsandroid.*
import com.android.szparag.myextensionsbase.containsOneItem
import timber.log.Timber
import kotlin.math.max

//todo: navigator should be singleton with builder pattern
//todo: how about persistentElement is NavigationBar at the bottom?
//todo: or what if there are multiple persistentElements?
//todo: what if there should be multiple Screens at once? eg Tablet UI or Spotify bottom play controls
class MyNavigator(
    private val globalContainer: RelativeLayout,
    private val inflater: LayoutInflater,
    private val persistentElement: Screen? = null,
    private val closeAppRequestResponse: () -> (Unit),
    private val onlyOneDialogLimit: Boolean = true,
    private val loggingEnabled: Boolean = false
) : Navigator {


  private val dialogStack = NavigationStack() //todo: maybe this stack should not hold screen, but other data
  //todo: like position in container, layer, screenClass etc
  //todo: + itself should have a data like stackPositionInContainerBeginIndex + EndIndex
  private val foregroundStack = NavigationStack()
  private val backgroundStack = NavigationStack()

  private lateinit var screensContainer: ViewGroup
  private lateinit var persistentContainer: ViewGroup

  init {
    Timber.d("init, dialogStack: $dialogStack, foregroundStack: $foregroundStack, backgroundStack: $foregroundStack")
    setupSubContainers(globalContainer)
    dialogStack.onScreenPushedListener = this::onDialogPushed
    dialogStack.onScreenPoppedListener = this::onDialogPopped
    foregroundStack.onScreenPushedListener = this::onForegroundPushed
    foregroundStack.onScreenPoppedListener = this::onForegroundPopped
    backgroundStack.onScreenPushedListener = this::onBackgroundPushed
    backgroundStack.onScreenPoppedListener = this::onBackgroundPopped
  }

  private fun setupSubContainers(globalContainer: RelativeLayout) {
    Timber.d("setupSubContainers, globalContainer: $globalContainer")
    persistentElement?.let { element ->
      persistentContainer = inflater.inflate(element.layoutResource, globalContainer, false) as ViewGroup
      persistentContainer.generateId()
      val layoutParams = persistentContainer.layoutParams.toRelativeLayoutParams()
      layoutParams.alignParentTop()
      globalContainer.addView(persistentContainer, layoutParams)
      (persistentContainer as? MviView<*>
          ?: throw NotImplementingMviViewException()).navigationDelegate = this
      persistentContainer.show()
    }
    screensContainer = FrameLayout(inflater.context)
    val layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    if (::persistentContainer.isInitialized) layoutParams.belowId(persistentContainer.id)
    globalContainer.addView(screensContainer, layoutParams)
  }


  //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxtodo: dialogs (just showing)
  //todo: transition policies

  override fun goToScreen(screen: Screen) {
    Timber.d("goToScreen, screen: $screen")
    handleInTransitionPolicy(screen.transitionInPolicy, getStackByLayer(screen.layer))
    goToScreen(screen, getStackByLayer(screen.layer))
  }

  private fun handleInTransitionPolicy(transitionInPolicy: NavigationTransitionInPolicy, stack: NavigationStack) {
    Timber.d("handleInTransitionPolicy, transitionInPolicy: $transitionInPolicy, stack: $stack")
    when (transitionInPolicy) {
      is KILL_LAST -> {
        require(transitionInPolicy.itemCount > 0, { throw TransitionPolicyTooLowItemCountException() })
        stack.removeLastItems(transitionInPolicy.itemCount)
      }
      is KILL_ALL_PREVIOUS -> {
        stack.removeAllItems()
      }
      is DEFAULT_NONE -> {
      }
    }
  }

  private fun goToScreen(screen: Screen, stack: NavigationStack) {
    Timber.d("goToScreen, screen: $screen, stack: $stack")
    stack.push(screen)
  }

  override fun goBack(layer: NavigationLayer) {
    Timber.d("goBack")
    when (layer) {
      DIALOG -> dialogStack.pop()
      BACKGROUND -> if (!backgroundStack.containsOneItem()) backgroundStack.pop()
      PERSISTENT -> throw MutatingPersistableElementException()
      FOREGROUND -> goBackDefault()
    }
    backgroundStack.isEmpty()
  }


  private fun goBackDefault() {
    Timber.d("goBackDefault")
    if (dialogStack.isNotEmpty()) {
      dialogStack.pop()
    } else {
      foregroundStack.pop()
      if (foregroundStack.isEmpty()) {
        closeAppRequestResponse.invoke()
      }
    }
  }

  override fun onHandleFirstRender(screen: Screen) {
    Timber.d("onHandleFirstRender, screen: $screen")
//    globalContainer.childByClass(screen.viewClass)?.let { view ->
//      constructScreenAnimation(view, screen.transitionInAnimation)
//    }
  }

  override fun handleBackPress() {
    Timber.d("handleBackPress")
    goBack()
  }

  private fun onDialogPushed(screen: Screen) {
    Timber.d("onDialogPushed, screen: $screen")
    handleOutgoingScreen(dialogStack.peekPrevious())
    handleIncomingScreen(dialogStack.peekCurrent())
  }

  private fun onDialogPopped(screen: Screen?) {
    Timber.d("onDialogPopped, screen: $screen")
    handleOutgoingScreen(screen)
    handleIncomingScreen(dialogStack.peekCurrent())
  }

  private fun onForegroundPushed(screen: Screen) {
    Timber.d("onForegroundPushed, screen: $screen")
    handleOutgoingScreen(foregroundStack.peekPrevious())
    handleIncomingScreen(foregroundStack.peekCurrent())
  }

  private fun onForegroundPopped(screen: Screen?) {
    Timber.d("onForegroundPopped, screen: $screen")
    handleOutgoingScreen(screen)
    handleIncomingScreen(foregroundStack.peekCurrent())
  }

  private fun onBackgroundPushed(screen: Screen) {
    Timber.d("onBackgroundPushed, screen: $screen")
    handleOutgoingScreen(backgroundStack.peekPrevious())
    handleIncomingScreen(backgroundStack.peekCurrent())
  }

  private fun onBackgroundPopped(screen: Screen?) {
    Timber.d("onBackgroundPopped, screen: $screen")
    handleOutgoingScreen(screen)
    handleIncomingScreen(backgroundStack.peekCurrent())
  }


  private fun handleIncomingScreen(screen: Screen?) {
    Timber.d("handleIncomingScreen, screen: $screen")
    screen?.let {
      handleIncomingScreen(
          screen.layoutResource,
          screen.transitionInAnimation,
          screen.layer,
          when (screen.layer) {
            DIALOG -> globalContainer
            FOREGROUND -> screensContainer
            BACKGROUND -> screensContainer
            else -> throw InflatingPersistentElementOutsideConstructorException()
          }
      )
    }
  }

  private fun handleIncomingScreen(layoutId: LayoutId, transitionInAnimation: NavigationTransitionInAnimation, layer: NavigationLayer, container: ViewGroup) {
    Timber.d("handleIncomingScreen, layoutId: $layoutId, transitionInAnimation: $transitionInAnimation, layer: $layer, container: $container")
    inflateScreen(inflater, container, layoutId).apply {
      this as? MviView<*> ?: throw NotImplementingMviViewException()
      constructScreenAnimation(this, transitionInAnimation)?.start()
      this.navigationDelegate = this@MyNavigator
      container.addView(this, when (layer) {
        DIALOG -> -1
        FOREGROUND -> -1
        BACKGROUND -> max(0, container.childCount - 1)
        PERSISTENT -> -1
      })
    }
  }


  private fun handleOutgoingScreen(screen: Screen?) {
    Timber.d("handleOutgoingScreen, screen: $screen")
    screen?.let {
      handleOutgoingScreen(
          screen.viewClass,
          screen.transitionOutAnimation,
          when (screen.layer) {
            DIALOG -> globalContainer
            FOREGROUND -> screensContainer
            BACKGROUND -> screensContainer
            else -> throw MutatingPersistableElementException()
          }
      )
    }

  }

  private fun handleOutgoingScreen(viewClass: Class<*>, transitionOutAnimation: NavigationTransitionOutAnimation, container: ViewGroup) {
    Timber.d("handleOutgoingScreen, viewClass: $viewClass, transitionOutAnimation: $transitionOutAnimation, container: $container")
    container.childByClass(viewClass)?.let { view ->
      constructScreenAnimation(view, transitionOutAnimation)?.start()
      container.removeViewInLayout(view)
    } ?: throw CouldntFindViewInHierarchyException(viewClass)
  }

  private fun inflateScreen(inflater: LayoutInflater, container: ViewGroup, @LayoutRes screenLayoutResource: LayoutId): View {
    Timber.d("inflateScreen, inflater: $inflater, screenLayoutResource: $screenLayoutResource")
    return inflater.inflate(screenLayoutResource, container, false)
  }

  private fun constructScreenAnimation(target: View, animation: NavigationTransitionAnimation): ViewPropertyAnimator? {
    Timber.d("constructScreenAnimation, target: $target, animation: $animation")
    return when (animation) {
      is NavigationTransitionInAnimation.FADE_IN -> {
        target
            .animate()
            .alpha(1f)
            .duration(animation.duration)
            .interpolator(animation.interpolator)
            .listener(
                onAnimationStart = {
                  target.alpha = 0f
                  target.show()
                }
            )
      }
      is NavigationTransitionInAnimation.MOVE_IN -> {
        target
            .animate()
            .alpha(1f)
            .duration(animation.duration)
            .interpolator(animation.interpolator)
            .listener(
                onAnimationStart = {
                  target.alpha = 0f
                  target.show()
                }
            )

      }
      is NavigationTransitionOutAnimation.FADE_OUT -> {
        target
            .animate()
            .alpha(0f)
            .duration(animation.duration)
            .interpolator(animation.interpolator)
            .listener(
                onAnimationEnd = {
                  target.hide()
                }
            )

      }
      is NavigationTransitionOutAnimation.MOVE_OUT -> {
        target
            .animate()
            .alpha(1f)
            .duration(animation.duration)
            .interpolator(animation.interpolator)
            .listener(
                onAnimationStart = {
                  target.alpha = 0f
                  target.show()
                }
            )

      }
      is NavigationTransitionAnimation.INSTANT -> {
        target.show()
        null
      }
    }
  }

  private fun getStackByLayer(layer: NavigationLayer) =
      when (layer) {
        DIALOG -> dialogStack
        FOREGROUND -> foregroundStack
        BACKGROUND -> backgroundStack
        PERSISTENT -> throw InflatingPersistentElementOutsideConstructorException()
      }
}