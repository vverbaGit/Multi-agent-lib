package common.test

import common.implementation.check.LessCheck
import common.implementation.check.MoreCheck
import common.interfaces.DirectoryFacilitator
import java.lang.Math.random

class ProducerAgentBehavior<T>(
    x: T
) : AbstractAgentBehavior<T>(x, mutableListOf(
    MoreCheck(-10f - random().toFloat() * 10f),
    LessCheck(10f + random().toFloat() * 10f)
)) {

    override fun endpoint(mts: DirectoryFacilitator): String {
        return "${mts.parent.identifier}@consumers"
    }
}