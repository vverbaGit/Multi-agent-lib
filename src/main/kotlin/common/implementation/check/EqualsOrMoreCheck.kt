package common.implementation.check

import common.interfaces.Check

class EqualsOrMoreCheck(private val value: Number) : Check<Number> {

    override fun test(x: Number): Boolean {
        return x.toDouble() >= value.toDouble()
    }
}