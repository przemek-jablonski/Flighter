package com.android.szparag.mvi.views

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
interface MviView<in VS : Any> {

  fun instantiatePresenter()

  fun attachToPresenter()

  fun detachFromPresenter()

  fun render(state: VS)

}