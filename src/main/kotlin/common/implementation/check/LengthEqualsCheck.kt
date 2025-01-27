package common.implementation.check

import common.interfaces.Check

/**
 * Length equals check
 *
 * @property value
 * @constructor Create empty Length equals check
 */
class LengthEqualsCheck(private val value: Number) : Check<String> {

    override fun test(x: String): Boolean {
        return x.length == value.toInt()
    }
}