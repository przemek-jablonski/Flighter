package com.android.szparag.myextensionsandroid

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.view.animation.Animation
import com.android.szparag.myextensionsbase.nullString

fun Intent.asString() = StringBuilder(1024).append(
    "Intent: [action: ${this.action}, cat: ${this.categories}, component: ${this.component}, " + "flags: ${this.flags} bundle: ${this.extras.asString(
        StringBuilder(1024))}]").toString()

fun Bundle?.asString(stringBuilder: StringBuilder): String {
  if (this == null) return nullString()
  val keys = this.keySet()
  stringBuilder.append("[")
  keys.forEach { key -> stringBuilder.append("$key: ${this.get(key)}, ") }
  stringBuilder.delete(stringBuilder.length - 2, stringBuilder.length - 1).append("]")
  return stringBuilder.toString()
}

fun View.asString() = asShortString()

fun View.asShortString() = "${this.resources.getResourceTypeName(id)}/${this.resources.getResourceEntryName(id)} (${this::class.java.simpleName}@${hashCode()})"

internal val View.complexString
  get() = StringBuilder(2048).append(
      "${asShortString()}\n" + "\t${toString()}\n" + "\tid: $id, tag: $tag\n" + "\tvisibility: ${visibilityAsString()}, alpha: ${this.alpha}\n" + /*"\tbackground: ${background?.complexString}\n" + **/"\tpaddings (top, bot, left, right): ($paddingTop, $paddingBottom, " + "$paddingLeft, $paddingRight)\n" + "\tframe: (top, bot, left, right): ($top, $bottom, $left, $right)\n" + "\tscale (x, y): ($scaleX, $scaleY)\n" + "\trotation: (x, y): ($rotationX, $rotationY)\n" + "\t(x, y): ($x, $y) layoutParams: ${layoutParams?.asString()}\n" + "\tmeasuredWidth: $measuredWidth, measuredHeight: $measuredHeight\n" + "\tanimation: $animation\n" + "\tparent: ${parent?.toString()}" + "").toString()

fun Drawable.asString() = if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
  asStringPostKitkat()
} else {
  asStringPreKitkat()
}

fun Drawable.asStringPreKitkat() = "${this::class.java.simpleName}@${hashCode()}, bounds: ${this.bounds}, iHeight: ${this.intrinsicHeight}, " + "iWidth: ${this.intrinsicWidth}, opacity: ${this.opacity}, visible: ${this.isVisible}"

@RequiresApi(VERSION_CODES.KITKAT)
fun Drawable.asStringPostKitkat() = "${this::class.java.simpleName}@${hashCode()}, bounds: ${this.bounds}, iHeight: ${this.intrinsicHeight}, " + "iWidth: ${this.intrinsicWidth}, alpha: ${this.alpha}, opacity: ${this.opacity}, visible: ${this.isVisible}"

fun Animation.asString() = "${this::class.java.simpleName}@${hashCode()}, duration: $duration, offset: $startOffset, starttime: " + "${this.startTime}, repeats: $repeatCount, interpolator: ${interpolator::class.java.simpleName}, fillEnabled: ${this.isFillEnabled}"




