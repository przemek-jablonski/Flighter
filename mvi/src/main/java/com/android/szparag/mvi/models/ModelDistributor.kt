package com.android.szparag.mvi.models

import io.reactivex.Observable

interface ModelDistributor<VS : Any> {

  fun replaceModel(newModel: VS)

  fun getModels(): Observable<VS> //todo: to flowable

  fun getLatestModel(): VS?

}