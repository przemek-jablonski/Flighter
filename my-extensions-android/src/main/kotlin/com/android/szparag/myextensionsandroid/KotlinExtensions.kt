package com.android.szparag.myextensionsandroid

inline fun <A, B, C> Any.let(a: A?, b: B?, block: (A, B) -> C) {
  if (a != null && b != null) {
    block.invoke(a, b)
  }
}