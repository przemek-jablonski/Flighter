package com.android.szparag.myextensionsandroid

import android.app.Activity
import android.content.Context
import android.content.Intent

typealias Pixel = Int
typealias Dp = Float

fun Activity.getDisplayMetrics() = resources.displayMetrics

infix fun Context.startActivity(targetActivity: Class<*>) = startActivity(Intent(this, targetActivity))
