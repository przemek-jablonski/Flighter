package com.android.szparag.flighter.common.navigation

import android.view.LayoutInflater
import android.widget.FrameLayout
import com.android.szparag.mvi.navigator.BaseMviNavigator

class FlighterNavigator(
    globalContainer: FrameLayout,
    inflater: LayoutInflater
) : BaseMviNavigator(globalContainer, inflater)