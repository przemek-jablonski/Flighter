package com.android.szparag.mvi.presenters

import com.android.szparag.mvi.views.MviView

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
interface MviPresenter<in V : MviView<VS>, in VS : Any> {

//  fun onViewBound(view: V)

  fun attachView(view: V)

  fun detachView(view: V)

  fun onViewAttached(view: V)

  fun onViewDetached(view: V)

}