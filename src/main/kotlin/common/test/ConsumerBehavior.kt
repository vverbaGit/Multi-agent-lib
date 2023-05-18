package common.test

import common.implementation.AgentPlatform
import common.implementation.check.LessCheck
import common.implementation.check.MoreCheck
import common.interfaces.Agent
import common.interfaces.Check
import common.interfaces.DirectoryFacilitator
import common.message.Message

class ConsumerAgentBehaviour<T>(
    x: T,
    checker: MutableList<Check<Any>> = mutableListOf(
        MoreCheck(3f + Math.random().toFloat() * 3f),
        LessCheck(-3f - Math.random().toFloat() * 3f)
    )
) : AbstractAgentBehavior<T>(x, checker) {

    override suspend fun onReceive(message: Message, agent: Agent, mts: DirectoryFacilitator) {
        if (message.code == AgentPlatform.CODE_REGISTRATION) {
            mts.send(Message(agent.identifier, endpoint(mts), code = CODE_CHECK, data = x))
        } else {
            super.onReceive(message, agent, mts)
        }
    }

    override fun endpoint(mts: DirectoryFacilitator): String {
        return "${mts.parent.identifier}@producers"
    }
}