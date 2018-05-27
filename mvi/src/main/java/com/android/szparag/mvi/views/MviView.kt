package com.android.szparag.mvi.views

import com.android.szparag.columbus.ColumbusNavigableScreen
import com.szparag.android.mypermissions.PermissionRequestsDelegate

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
interface MviView<in VS : Any>: ColumbusNavigableScreen, PermissionRequestsDelegate {

  fun render(state: VS)

}