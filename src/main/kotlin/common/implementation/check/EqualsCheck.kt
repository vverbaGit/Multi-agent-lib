package common.implementation.check

import common.interfaces.Check

class EqualsCheck(private val value: Any) : Check<Any> {

    override fun test(x: Any): Boolean {
        return x == value
    }
}