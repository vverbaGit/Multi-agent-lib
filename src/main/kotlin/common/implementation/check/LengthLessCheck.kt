package common.implementation.check

import common.interfaces.Check

/**
 * Length less check
 *
 * @property value
 * @constructor Create empty Length less check
 */
class LengthLessCheck(private val value: Number) : Check<String> {

    override fun test(x: String): Boolean {
        return x.length < value.toInt()
    }
}