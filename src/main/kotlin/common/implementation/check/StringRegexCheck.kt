package common.implementation.check

import common.interfaces.Check

class StringRegexCheck(private val value: Regex) : Check<String> {

    override fun test(x: String): Boolean {
        return value.matches(x)
    }
}