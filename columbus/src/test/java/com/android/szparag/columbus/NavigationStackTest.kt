package com.android.szparag.columbus

import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

class NavigationStackTest {

  @Mock
  private lateinit var onScreenPushedListener: OnScreenPushedListener
  @Mock
  private lateinit var onScreenPoppedListener: OnScreenPoppedListener
  @Mock
  private lateinit var screenA: Screen
  @Mock
  private lateinit var screenB: Screen

  private lateinit var stack: NavigationStack

  private fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
  }

  @Suppress("UNCHECKED_CAST")
  private fun <T> uninitialized(): T = null as T

  @Before
  fun setupTests() {
    initMocks(this)
    stack = NavigationStack()
  }

  @Test
  fun `only screenPushedListener should be called on push(item) operation (if not null) (single push)`() {
    stack.onScreenPushedListener = onScreenPushedListener
    stack.push(screenA)
    verify(onScreenPushedListener, times(1)).invoke(screenA)
    verify(onScreenPoppedListener, never()).invoke(any())
  }

  @Test
  fun `only screenPushedListener should be called on push(item) operation (if not null) (multiple pushes)`() {
    stack.onScreenPushedListener = onScreenPushedListener
    stack.push(screenA)
    stack.push(screenB)
    verify(onScreenPushedListener, times(2)).invoke(any())
    verify(onScreenPoppedListener, never()).invoke(any())
  }

  @Test
  fun `only screenPoppedListener should be called on pop(item) operation (if not null) (populated list) (single pop)`() {
    stack.onScreenPoppedListener = onScreenPoppedListener
    stack.push(screenA)
    stack.push(screenB)
    stack.pop()
    verify(onScreenPoppedListener, times(1)).invoke(screenB)
    verify(onScreenPushedListener, never()).invoke(any())
  }


  @Test
  fun `only screenPoppedListener should be called on pop(item) operation (if not null) (populated list) (multiple pops)`() {
    stack.onScreenPoppedListener = onScreenPoppedListener
    stack.push(screenA)
    stack.push(screenB)
    stack.pop()
    stack.pop()
    verify(onScreenPoppedListener, times(2)).invoke(any())
    verify(onScreenPushedListener, never()).invoke(any())
  }

  @Test
  fun `screenPushedListener and screenPoppedListener should be called once on single push and single pop (if not null)`() {
    stack.onScreenPoppedListener = onScreenPoppedListener
    stack.onScreenPushedListener = onScreenPushedListener
    stack.push(screenA)
    stack.pop()
    verify(onScreenPushedListener, times(1)).invoke(screenA)
    verify(onScreenPoppedListener, times(1)).invoke(screenA)
  }

  @Test
  fun `screenPushedListener and screenPoppedListener should be called multiple times on multiple pushes and pops (if not null)`() {
    stack.onScreenPoppedListener = onScreenPoppedListener
    stack.onScreenPushedListener = onScreenPushedListener
    stack.push(screenA)
    stack.push(screenB)
    stack.pop()
    stack.pop()
    verify(onScreenPushedListener, times(2)).invoke(any())
    verify(onScreenPoppedListener, times(2)).invoke(any())
  }

  @Test
  fun `screenPushedListener and screenPoppedListener should be not called if null (not assigned)`() {
    stack.push(screenA)
    stack.push(screenB)
    stack.pop()
    stack.pop()
    verify(onScreenPushedListener, never()).invoke(any())
    verify(onScreenPoppedListener, never()).invoke(any())
  }

  @Test
  fun `screenPushedListener should not be called on push(item) operation (if is null)`() {
    stack.push(screenA)
    verify(onScreenPushedListener, never()).invoke(screenA)
  }


  @Test
  fun `screenPoppedListener should not be called on pop(item) operation (if is null)`() {
    stack.push(screenA)
    verify(onScreenPoppedListener, never()).invoke(screenA)
  }

  @Test
  fun `push() should add to stack (single item scenario)`() {
    stack.push(screenA)
    assertTrue(stack.size == 1)
  }

  @Test
  fun `push() should add to stack (multiple item scenario)`() {
    stack.push(screenA)
    stack.push(screenB)
    assertTrue(stack.size == 2)
  }

  @Test
  fun `pop() should return top item (on populated list)`() {
    stack.push(screenA)
    stack.push(screenB)
    assertTrue(stack.pop() === screenB)
    assertTrue(stack.pop() === screenA)
  }

  @Test
  fun `peekCurrent() should return top item (on populated list)`() {
    stack.push(screenA)
    stack.push(screenB)
    assertTrue(stack.peekCurrent() === screenB)
    assertTrue(stack.peekCurrent() === screenB)
  }

  @Test
  fun `peekPrevious() should return top item -1 (on populated list)`() {
    stack.push(screenA)
    stack.push(screenB)
    assertTrue(stack.peekPrevious() === screenA)
    assertTrue(stack.peekPrevious() === screenA)
  }

  @Test
  fun `peekCurrent() should return null (on non populated list)`() {
    assertTrue(stack.peekCurrent() == null)
  }

  @Test
  fun `peekPrevious() should return null (on non populated list)`() {
    assertTrue(stack.peekPrevious() == null)
  }

  @Test
  fun `pop() should return null (on non-populated list)`() {
    assertTrue(stack.pop() == null)
  }

}