package common.implementation.check

import common.interfaces.Check

/**
 * Less check
 *
 * @param T
 * @property value
 * @constructor Create empty Less check
 */
class LessCheck<T>(private val value: T) : Check<T> {

    override fun test(x: T): Boolean {
        return (x as Number).toDouble() < (value as Number).toDouble()
    }
}