package com.android.szparag.flighter.common

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.szparag.myextensionsbase.emptyMutableList
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

abstract class GenericRecyclerViewAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    protected var itemsList: MutableList<T> = emptyMutableList())
  : RecyclerView.Adapter<VH>() {

  private val itemClickedSubject: Subject<T> = PublishSubject.create()
  private val viewClickedSubject: Subject<View> = PublishSubject.create()

  abstract fun update(updatedList: List<T>)

  abstract fun onBind(holder: VH, item: T)

  protected fun update(updatedList: List<T>, diffUtilCallback: DiffUtil.Callback, detectMoves: Boolean = true) {
    DiffUtil.calculateDiff(diffUtilCallback, detectMoves).also { diffResult ->
      itemsList.clear()
      itemsList.addAll(updatedList)
      diffResult.dispatchUpdatesTo(this)
    }
  }

  override fun onBindViewHolder(holder: VH, position: Int) {
    val item = itemsList[position]
    onBind(holder, item)
    holder.itemView.setOnClickListener { view ->
      itemClickedSubject.onNext(item)
      viewClickedSubject.onNext(view)
    }
  }


  override fun getItemCount() = itemsList.size

  fun getItemClicks(): Observable<T> = itemClickedSubject

  fun getViewClicks(): Observable<View> = viewClickedSubject
}