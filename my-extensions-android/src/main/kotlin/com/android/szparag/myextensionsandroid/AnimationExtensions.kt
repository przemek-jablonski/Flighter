package com.android.szparag.myextensionsandroid

import android.animation.Animator
import android.animation.TimeInterpolator
import android.view.View
import android.view.ViewPropertyAnimator

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 05/04/2018.
 */

typealias AnimationListenerCallback = (Animator) -> (Unit)

private val animationListenerCallbackStub: AnimationListenerCallback = {}

infix fun <T : View> T.fadeOutWith(interpolator: TimeInterpolator) {
  animate().alpha(0f).setInterpolator(interpolator).setListener(object : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationStart(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) {
      this@fadeOutWith.hide()
    }
  }).start()
}

infix fun <T : View> T.fadeInWith(interpolator: TimeInterpolator) {
  animate().alpha(1f).setInterpolator(interpolator).setListener(object : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) {}
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationStart(animation: Animator?) {
      this@fadeInWith.alpha = 0f
      this@fadeInWith.show()
    }
  }).start()
}


fun ViewPropertyAnimator.listener(
    onAnimationStart: AnimationListenerCallback = animationListenerCallbackStub,
    onAnimationEnd: AnimationListenerCallback = animationListenerCallbackStub,
    onAnimationRepeat: AnimationListenerCallback = animationListenerCallbackStub,
    onAnimationCancel: AnimationListenerCallback = animationListenerCallbackStub
): ViewPropertyAnimator =
    this.setListener(object : Animator.AnimatorListener {
      override fun onAnimationStart(animation: Animator?) {
        animation?.let { onAnimationStart.invoke(it) }
      }

      override fun onAnimationEnd(animation: Animator?) {
        animation?.let { onAnimationEnd.invoke(it) }
      }

      override fun onAnimationRepeat(animation: Animator?) {
        animation?.let { onAnimationRepeat.invoke(it) }
      }

      override fun onAnimationCancel(animation: Animator?) {
        animation?.let { onAnimationCancel.invoke(it) }
      }

    }
    )