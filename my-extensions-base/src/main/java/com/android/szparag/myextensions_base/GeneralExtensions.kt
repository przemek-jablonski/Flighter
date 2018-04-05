package com.android.szparag.myextensions_base

import java.util.ArrayList
import java.util.Arrays

typealias Millis = Long
typealias Seconds = Long

fun nullString() = "NULL"
fun emptyString() = ""
fun invalidStringValue() = emptyString()
fun invalidIntValue() = -1
fun invalidLongValue() = -1L
fun invalidFloatValue() = -1f

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