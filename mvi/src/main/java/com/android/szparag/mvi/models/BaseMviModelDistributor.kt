package com.android.szparag.mvi.models

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class BaseMviModelDistributor<VS : Any> : ModelDistributor<VS> {

  private var model: VS? = null
  private val modelSubject = BehaviorSubject.create<VS>()

  override fun replaceModel(newModel: VS) = with(newModel) {
    model = this
    modelSubject.onNext(this)
  }

  override fun getModels(): Observable<VS> = modelSubject

}