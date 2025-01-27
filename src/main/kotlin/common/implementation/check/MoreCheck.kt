package common.implementation.check

import common.interfaces.Check

/**
 * More check
 *
 * @param T
 * @property value
 * @constructor Create empty More check
 */
class MoreCheck<T>(private val value: T) : Check<T> {

    override fun test(x: T): Boolean {
        return (x as Number).toDouble() > (value as Number).toDouble()
    }
}