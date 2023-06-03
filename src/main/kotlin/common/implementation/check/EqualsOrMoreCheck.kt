package common.implementation.check

import common.interfaces.Check

/**
 * Equals or more check
 *
 * @property value
 * @constructor Create empty Equals or more check
 */
class EqualsOrMoreCheck(private val value: Number) : Check<Number> {

    override fun test(x: Number): Boolean {
        return x.toDouble() >= value.toDouble()
    }
}