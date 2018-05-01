package com.android.szparag.myextensionsandroid

import android.view.ViewGroup

fun ViewGroup.removeFirst() = removeViewAt(0)
fun ViewGroup.removeLast() = removeViewAt(childCount - 1)