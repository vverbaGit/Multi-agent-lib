package common.implementation.check

import common.interfaces.Check

class NotEqualsCheck(private val value: Any) : Check<Any> {

    override fun test(x: Any): Boolean {
        return x != value
    }
}