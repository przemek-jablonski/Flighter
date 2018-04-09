package com.android.szparag.mvi.views

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
interface MviView<in VS : Any> {

  //todo:
  //open var animation showing
  //open var animation hiding
  //open var animation transitioning in
  //open var animation transitioning out
  //animation showing - hiding, same type but inverted (by default)
  //animation trans in - trans out, same type but inverted (by default)
  //few default primitives defined

//  fun instantiatePresenter()
//
//  fun attachToPresenter()
//
//  fun detachFromPresenter()

  fun render(state: VS)

}