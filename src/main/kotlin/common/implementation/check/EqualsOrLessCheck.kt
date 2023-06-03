package common.implementation.check

import common.interfaces.Check

/**
 * Equals or less check
 *
 * @property value
 * @constructor Create empty Equals or less check
 */
class EqualsOrLessCheck(private val value: Number) : Check<Number> {

    override fun test(x: Number): Boolean {
        return x.toDouble() <= value.toDouble()
    }
}