package com.android.szparag.myextensionsandroid

import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.RelativeLayout.ALIGN_PARENT_TOP
import android.widget.RelativeLayout.BELOW

fun RelativeLayout.LayoutParams.alignParentTop() = addRule(ALIGN_PARENT_TOP)
infix fun RelativeLayout.LayoutParams.belowId(viewId: Int) = addRule(BELOW, viewId)

fun ViewGroup.LayoutParams.toRelativeLayoutParams() = RelativeLayout.LayoutParams(this)