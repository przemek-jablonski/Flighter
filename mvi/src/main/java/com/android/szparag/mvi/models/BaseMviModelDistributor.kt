package com.android.szparag.mvi.models

import io.reactivex.subjects.BehaviorSubject

open class BaseMviModelRepository<VS : Any> : ModelRepository<VS> {

  private val modelSubject = BehaviorSubject.create<VS>()

  override fun replaceModel(newModel: VS) = modelSubject.onNext(newModel)

  override fun getModels() = modelSubject

  override fun getLatestModel(): VS? = modelSubject.value //todo: !!s
}