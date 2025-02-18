package common.implementation.check

import common.interfaces.Check

/**
 * Length more check
 *
 * @property value
 * @constructor Create empty Length more check
 */
class LengthMoreCheck(private val value: Number) : Check<String> {

    override fun test(x: String): Boolean {
        return x.length > value.toInt()
    }
}