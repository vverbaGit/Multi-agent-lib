package common.implementation.check

import common.interfaces.Check

/**
 * Not equals check
 *
 * @property value
 * @constructor Create empty Not equals check
 */
class NotEqualsCheck(private val value: Any) : Check<Any> {

    override fun test(x: Any): Boolean {
        return x != value
    }
}