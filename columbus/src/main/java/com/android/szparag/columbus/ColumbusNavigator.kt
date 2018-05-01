package com.android.szparag.columbus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewPropertyAnimator
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.android.szparag.columbus.NavigationLayer.*
import com.android.szparag.columbus.NavigationTransitionInPolicy.*
import com.android.szparag.myextensionsandroid.*
import com.android.szparag.myextensionsbase.containsOneItem
import kotlin.math.max

//todo: navigator should be singleton with builder pattern
//todo: how about persistentElement is NavigationBar at the bottom?
//todo: or what if there are multiple persistentElements?
//todo: what if there should be multiple Screens at once? eg Tablet UI or Spotify bottom play controls
class ColumbusNavigator(
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
    ColumbusLogger.navigatorLog("init, dialogStack: $dialogStack, foregroundStack: $foregroundStack, backgroundStack: $foregroundStack")
    setupSubContainers(globalContainer)
    dialogStack.onScreenPushedListener = this::onDialogPushed
    dialogStack.onScreenPoppedListener = this::onDialogPopped
    foregroundStack.onScreenPushedListener = this::onForegroundPushed
    foregroundStack.onScreenPoppedListener = this::onForegroundPopped
    backgroundStack.onScreenPushedListener = this::onBackgroundPushed
    backgroundStack.onScreenPoppedListener = this::onBackgroundPopped
  }

  private fun setupSubContainers(globalContainer: RelativeLayout) {
    ColumbusLogger.navigatorLog("setupSubContainers, globalContainer: $globalContainer")
    persistentElement?.let { element ->
      persistentContainer = inflater.inflate(element.layoutResource, globalContainer, false) as ViewGroup
      persistentContainer.generateId()
      val layoutParams = persistentContainer.layoutParams.toRelativeLayoutParams()
      layoutParams.alignParentTop()
      globalContainer.addView(persistentContainer, layoutParams)
      (persistentContainer as? ColumbusNavigableScreen
          ?: throw NotImplementingNavigableViewException()).navigationDelegate = this
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
    ColumbusLogger.navigatorLog("goToScreen, screen: $screen")
    goToScreen(screen, getStackByLayer(screen.layer))
  }

  private fun goToScreen(screen: Screen, stack: NavigationStack) {
    ColumbusLogger.navigatorLog("goToScreen, screen: $screen, stack: $stack")
    stack.push(screen)
  }

  override fun goBack(layer: NavigationLayer) {
    ColumbusLogger.navigatorLog("goBack")
    when (layer) {
      DIALOG -> dialogStack.pop()
      BACKGROUND -> if (!backgroundStack.containsOneItem()) backgroundStack.pop()
      PERSISTENT -> throw MutatingPersistableElementException()
      FOREGROUND -> goBackDefault()
    }
    backgroundStack.isEmpty()
  }


  private fun goBackDefault() {
    ColumbusLogger.navigatorLog("goBackDefault")
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
    ColumbusLogger.navigatorLog("onHandleFirstRender, screen: $screen")
//    globalContainer.childByClass(screen.viewClass)?.let { view ->
//      constructScreenAnimation(view, screen.transitionInAnimation)
//    }
  }

  override fun handleBackPress() {
    ColumbusLogger.navigatorLog("handleBackPress")
    goBack()
  }

  private fun onDialogPushed(screen: Screen) {
    ColumbusLogger.navigatorLog("onDialogPushed, screen: $screen")
    handleOutgoingScreen(dialogStack.peekPrevious())
    handleInTransitionPolicy(screen.transitionInPolicy, getStackByLayer(screen.layer))
    handleIncomingScreen(dialogStack.peekCurrent())
  }

  private fun onDialogPopped(screen: Screen?) {
    ColumbusLogger.navigatorLog("onDialogPopped, screen: $screen")
    handleOutgoingScreen(screen)
    handleIncomingScreen(dialogStack.peekCurrent())
  }

  private fun onForegroundPushed(screen: Screen) {
    ColumbusLogger.navigatorLog("onForegroundPushed, screen: $screen")
    foregroundStack.peekPrevious()?.let { previousScreen ->
      handleOutgoingScreen(previousScreen)
      handleOutTransitionPolicy(previousScreen.transitionOutPolicy, getStackByLayer(previousScreen.layer)) //todo: !!
    }
    foregroundStack.peekCurrent()?.let { currentScreen ->
      handleInTransitionPolicy(currentScreen.transitionInPolicy, getStackByLayer(currentScreen.layer)) //todo: !!
      handleIncomingScreen(currentScreen)
    }
  }

  private fun onForegroundPopped(screen: Screen?) {
    ColumbusLogger.navigatorLog("onForegroundPopped, screen: $screen")
    handleOutgoingScreen(screen)
    handleIncomingScreen(foregroundStack.peekCurrent())
  }

  private fun onBackgroundPushed(screen: Screen) {
    ColumbusLogger.navigatorLog("onBackgroundPushed, screen: $screen")
    handleOutgoingScreen(backgroundStack.peekPrevious())
    handleInTransitionPolicy(screen.transitionInPolicy, getStackByLayer(screen.layer))
    handleIncomingScreen(backgroundStack.peekCurrent())
    handleOutTransitionPolicy(screen.transitionOutPolicy, getStackByLayer(screen.layer))
  }

  private fun handleInTransitionPolicy(policy: NavigationTransitionInPolicy, stack: NavigationStack) {
    ColumbusLogger.navigatorLog("handleInTransitionPolicy, policy: $policy, stack: $stack")
    when (policy) {
      is KILL_LAST -> {
//        require(policy.itemCount > 0, { throw TransitionPolicyTooLowItemCountException() })
        stack.removeLastItemsPreserveTop(policy.itemCount)
      }
      is KILL_ALL_PREVIOUS -> {
        stack.removeAllItemsPreserveTop()
      }
      is DEFAULT_NONE -> {
      }
    }
  }

  private fun handleOutTransitionPolicy(policy: NavigationTransitionOutPolicy, stack: NavigationStack) {
    ColumbusLogger.navigatorLog("handleOutTransitionPolicy, policy: $policy, stack: $stack")
    when (policy) {
      is NavigationTransitionOutPolicy.KILL_ITSELF -> {
        stack.removeLastItemsPreserveTop(1)
      }
      is NavigationTransitionOutPolicy.KILL_LAST -> {
        stack.removeLastItemsPreserveTop(policy.itemCount, 2)
      }
      is NavigationTransitionOutPolicy.KILL_ALL_PREVIOUS -> {
        stack.removeAllItemsPreserveTop(2)
      }
      is NavigationTransitionOutPolicy.KILL_ALL -> {
        stack.removeAllItemsPreserveTop()
      }
      is NavigationTransitionOutPolicy.PERSISTENT_IN_STACK -> {
      }
      is NavigationTransitionOutPolicy.DEFAULT_NONE -> {
      }
    }
  }

  private fun onBackgroundPopped(screen: Screen?) {
    ColumbusLogger.navigatorLog("onBackgroundPopped, screen: $screen")
    handleOutgoingScreen(screen)
    handleIncomingScreen(backgroundStack.peekCurrent())
  }


  private fun handleIncomingScreen(screen: Screen?) {
    ColumbusLogger.navigatorLog("handleIncomingScreen, screen: $screen")
    screen?.let {
      handleIncomingScreen(
          screen.layoutResource,
          screen.transitionInAnimation,
          screen.layer,
          getContainerByLayer(screen.layer)
      )
    }
  }

  private fun handleIncomingScreen(layoutId: LayoutId, transitionInAnimation: NavigationTransitionInAnimation, layer: NavigationLayer, container: ViewGroup) {
    ColumbusLogger.navigatorLog("handleIncomingScreen, layoutId: $layoutId, transitionInAnimation: $transitionInAnimation, layer: $layer, container: $container")
    inflateScreen(inflater, container, layoutId).apply {
      this as? ColumbusNavigableScreen ?: throw NotImplementingNavigableViewException()
      constructScreenAnimation(this, transitionInAnimation)?.start()
      this.navigationDelegate = this@ColumbusNavigator
      container.addView(this, when (layer) {
        DIALOG -> -1
        FOREGROUND -> -1
        BACKGROUND -> max(0, container.childCount - 1)
        PERSISTENT -> -1
      })
    }
  }


  private fun handleOutgoingScreen(screen: Screen?) {
    ColumbusLogger.navigatorLog("handleOutgoingScreen, screen: $screen")
    screen?.let {
      handleOutgoingScreen(
          screen.viewClass,
          screen.transitionOutAnimation,
          getContainerByLayer(screen.layer)
      )
    }
  }

  private fun handleOutgoingScreen(viewClass: Class<*>, transitionOutAnimation: NavigationTransitionOutAnimation, container: ViewGroup) {
    ColumbusLogger.navigatorLog("handleOutgoingScreen, viewClass: $viewClass, transitionOutAnimation: $transitionOutAnimation, container: $container")
    container.childByClass(viewClass)?.let { view ->
      constructScreenAnimation(view, transitionOutAnimation)?.start()
      container.removeViewInLayout(view)
    } ?: throw CouldntFindViewInHierarchyException(viewClass)
  }

  private fun inflateScreen(inflater: LayoutInflater, container: ViewGroup, screenLayoutResource: LayoutId): View {
    ColumbusLogger.navigatorLog("inflateScreen, inflater: $inflater, screenLayoutResource: $screenLayoutResource")
    return inflater.inflate(screenLayoutResource, container, false)
  }

  private fun constructScreenAnimation(target: View, animation: NavigationTransitionAnimation): ViewPropertyAnimator? {
    ColumbusLogger.navigatorLog("constructScreenAnimation, target: $target, animation: $animation")
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

  private fun getContainerByLayer(layer: NavigationLayer) =
      when (layer) {
        DIALOG -> globalContainer
        FOREGROUND -> screensContainer
        BACKGROUND -> screensContainer
        else -> throw MutatingPersistableElementException()
      }
}