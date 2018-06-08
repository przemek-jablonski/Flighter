package com.android.szparag.flighter.common.interactors

import com.android.szparag.flighter.common.interactors.GenericFirebaseInteractor.FirebaseQueryModel.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.Single

typealias FirebaseQuery = Query
typealias FirebaseDatabaseReference = DatabaseReference

abstract class GenericFirebaseInteractor(protected val firebaseReference: FirebaseDatabaseReference) {

  sealed class FirebaseQueryModel {
    data class FirebaseQuerySuccessful(val snapshot: DataSnapshot) : FirebaseQueryModel()
    data class FirebaseQueryFailed(val error: DatabaseError) : FirebaseQueryModel()
  }

  protected fun FirebaseQuery.asSingle(): Single<FirebaseQueryModel> = //todo: is this correct? it should stream onComplete as well
      io.reactivex.Single.fromPublisher({
        this.addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onDataChange(snapshot: DataSnapshot) = it.onNext(FirebaseQuerySuccessful(snapshot))
          override fun onCancelled(error: DatabaseError) = it.onNext(FirebaseQueryFailed(error))
        })
      })

  //todo: I guess this should not return FQSuccessful, just FirebaseQueryModel
  protected fun FirebaseQuery.asObservable(): Observable<FirebaseQuerySuccessful> =
      io.reactivex.Observable.fromPublisher({
        this.addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onDataChange(snapshot: DataSnapshot) = it.onNext(FirebaseQuerySuccessful(snapshot))
          override fun onCancelled(error: DatabaseError) = it.onError(RuntimeException())
        })
      })

}

fun FirebaseQuerySuccessful.getChildren(): Iterable<DataSnapshot> = snapshot.children

fun DataSnapshot.asString() = String().apply { children.forEach { this.plus(it) } }

fun FirebaseQuerySuccessful.isEmpty() = snapshot.childrenCount == 0L