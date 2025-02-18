package common.implementation.check

import common.interfaces.Check

/**
 * String regex check
 *
 * @property value
 * @constructor Create empty String regex check
 */
class StringRegexCheck(private val value: Regex) : Check<String> {

    override fun test(x: String): Boolean {
        return value.matches(x)
    }
}