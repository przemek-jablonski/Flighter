package com.android.szparag.myextensionsandroid

fun <T> Collection<T>.doesntContain(element: T) = !contains(element)

//inclusive
fun <T: Comparable<T>> T.isInRange(rangeMin: T, rangeMax: T, inclusive: Boolean = true) =
    if (inclusive) this in rangeMin..rangeMax else rangeMin < this && rangeMax > this

inline fun <A, B, R> Any.let(a: A?, b: B?, block: (A, B) -> R) {
  if (a != null && b != null) {
    block.invoke(a, b)
  }
}

inline fun <A, B, C, R> Any.let(a: A?, b: B?, c: C?, block: (A, B, C) -> R) {
  if (a != null && b != null && c != null) {
    block.invoke(a, b, c)
  }
}