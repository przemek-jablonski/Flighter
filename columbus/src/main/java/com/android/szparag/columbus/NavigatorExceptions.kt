package com.android.szparag.columbus

private const val COULDNT_FIND_VIEW_IN_HIERARCHY_MESSAGE =
    "Couldn't find view in current hierarchy."
private const val INFLATING_PERSISTENT_ELEMENT_OUTSIDE_CONSTRUCTOR_MESSAGE =
    "Tried to show/inflate Persistent element outside of Navigator constructor. Consider passing Screen data for this element in Navigator constructor args."
private const val MUTATING_PERSISTENT_ELEMENT_MESSAGE =
    "Tried to mutate (inflate/remove) persistent element of the screen."
private const val NOT_IMPLEMENTING_NAVIGABLE_VIEW_EXCEPTION_MESSAGE =
    "Inflated view is not implementing ColumbusNavigableScreen interface. Double check whether your custom view extends classes with this interface, screenData for this view is valid or whether layout resource has correct layout in top level tag (should be class implementing NavigableScreen and not ConstraintLayout etc)."
private const val TRANSITION_POLICY_TOO_LOW_ITEM_COUNT_EXCEPTION_MESSAGE =
    "Parameter 'itemCount' used in NavigationTransitionInPolicy.KILL_LAST is equal or less than zero. It should be positive value."

sealed class NavigatorException(message: String) : RuntimeException(message)
class CouldntFindViewInHierarchyException(viewClass: Class<*>) : NavigatorException("$COULDNT_FIND_VIEW_IN_HIERARCHY_MESSAGE($viewClass)")
class InflatingPersistentElementOutsideConstructorException : NavigatorException(INFLATING_PERSISTENT_ELEMENT_OUTSIDE_CONSTRUCTOR_MESSAGE)
class MutatingPersistableElementException : NavigatorException(MUTATING_PERSISTENT_ELEMENT_MESSAGE)
class NotImplementingNavigableViewException : NavigatorException(NOT_IMPLEMENTING_NAVIGABLE_VIEW_EXCEPTION_MESSAGE)
class TransitionPolicyTooLowItemCountException: NavigatorException(TRANSITION_POLICY_TOO_LOW_ITEM_COUNT_EXCEPTION_MESSAGE)