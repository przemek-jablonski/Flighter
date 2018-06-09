package com.android.szparag.flighter.common.preferences

import io.reactivex.Flowable

interface SingleObjectPersistenceRepository<T : Any> {

  fun update(item: T)

  fun getItem(): Flowable<out List<T>>

}