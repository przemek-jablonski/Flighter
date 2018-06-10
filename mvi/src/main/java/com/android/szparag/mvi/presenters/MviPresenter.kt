package com.android.szparag.mvi.presenters

import com.android.szparag.mvi.views.MviView
import io.reactivex.Observable

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 02/04/2018.
 */
interface MviPresenter<in V : MviView<VS>, in VS : Any> {

  fun attachView(view: V)

  fun detachView(view: V)

  fun registerIntentProcessing(intentProcessing: Observable<out VS>)  //todo: invariant error

}