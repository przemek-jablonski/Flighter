package com.android.szparag.mvi.models

interface ModelRepository<VS : Any> : ModelDistributor<VS> {

  fun replaceModel(newModel: VS)

}