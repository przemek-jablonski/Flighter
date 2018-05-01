package com.android.szparag.myextensionsbase

import java.util.ArrayList
import java.util.Arrays
import kotlin.math.max

typealias Millis = Long
typealias Seconds = Long

fun nullString() = "NULL"
fun emptyString() = ""
fun <T: Any> emptyMutableList() = mutableListOf<T>()
fun invalidStringValue() = emptyString()
fun invalidIntValue() = -1
fun invalidLongValue() = -1L
fun invalidFloatValue() = -1f

inline fun <T> List<T>.findFirstIndexedOrNull(predicate: (T) -> Boolean): Pair<T?, Int> {
  return indices
      .firstOrNull { predicate(get(it)) }
      ?.let { Pair(get(it), it) }
      ?: Pair(null, invalidIntValue())
}

fun Int.decUntilZero() = max(0, dec())

fun getUnixTimestampMillis() = System.currentTimeMillis() //todo: does this work in every timezone?

fun getUnixTimestampSecs() = getUnixTimestampMillis() / 1000L


inline fun <T, R> Iterable<T>.map(transform: (T) -> R, initialCapacity: Int = count()) = mapTo(ArrayList(initialCapacity), transform)

fun <T : Any> Array<T>.arrayAsString() = Arrays.toString(this) ?: nullString()

fun <T : Any> List<T>.lastOr(default: T) = if (this.isNotEmpty()) this.last() else default


infix fun <T : Any> List<T>.shiftIndexesBy(shift: Int) {
  val shiftedArray = toMutableList()
  return forEachIndexed { index, _ -> shiftedArray[index] = this[(index + shift).rem(size)] }
}

fun Millis.toSeconds() = this / 1000
fun Millis.toMinutes() = this.toSeconds() / 60
fun Millis.toHours() = this.toMinutes() / 60

fun <T: Any> T?.requireNotNull(callback: (T) -> (Unit)) {
  requireNotNull(this)
  callback.invoke(this!!)
}

fun <E> Collection<E>.containsOneItem() = size == 1

