package common.interfaces

import java.util.function.Predicate

/**
 * Check interface that implements Predicate can be used to check availability to complete task by agent.
 *
 */
interface Check<T> : Predicate<T>