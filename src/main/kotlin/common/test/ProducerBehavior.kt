package common.test

import common.implementation.check.LessCheck
import common.implementation.check.MoreCheck
import common.interfaces.Check
import common.interfaces.DirectoryFacilitator
import java.lang.Math.random

class ProducerAgentBehavior<T>(
    x: T,
    checker: MutableList<Check<Any>> = mutableListOf(
        MoreCheck(-10f - random().toFloat() * 10f),
        LessCheck(10f + random().toFloat() * 10f)
    )
) : AbstractAgentBehavior<T>(x, checker) {

    override fun endpoint(mts: DirectoryFacilitator): String {
        return "${mts.parent.identifier}@consumers"
    }
}