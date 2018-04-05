package com.android.szparag.myextensions_android

import android.animation.Animator
import android.animation.TimeInterpolator
import android.view.View

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 05/04/2018.
 */

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