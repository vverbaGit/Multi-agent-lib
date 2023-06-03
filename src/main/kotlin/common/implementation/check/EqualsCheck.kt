package common.implementation.check

import common.interfaces.Check

/**
 * Equals check
 *
 * @property value
 * @constructor Create empty Equals check
 */
class EqualsCheck(private val value: Any) : Check<Any> {

    override fun test(x: Any): Boolean {
        return x == value
    }
}