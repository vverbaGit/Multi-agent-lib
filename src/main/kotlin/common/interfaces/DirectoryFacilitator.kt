package common.interfaces

import common.message.Message
import common.utils.DURATION_INFINITE
import kotlinx.coroutines.channels.Channel
import kotlinx.datetime.Clock
import kotlin.time.Duration

interface DirectoryFacilitator : Agent {

    suspend fun onPathUnresolved(message: Message)

    suspend fun df(
        name: String,
        mts: MessageTransportService? = null,
        capicity: Int = Channel.CONFLATED,
        init: suspend DirectoryFacilitator.() -> Unit
    )

    suspend fun agent(agent: Agent)

    suspend fun agent(
        name: String = "${Clock.System.now().toEpochMilliseconds()}",
        capacity: Int = Channel.CONFLATED,
        lifecycle: Duration = DURATION_INFINITE,
        behaviour: BehaviourWithRepository
    )

    suspend fun agent(
        name: String = "${Clock.System.now().toEpochMilliseconds()}",
        capacity: Int = Channel.CONFLATED,
        lifecycle: Duration = DURATION_INFINITE,
        block: suspend Agent.(Message, DirectoryFacilitator) -> Unit
    ) {
        val behaviour = BehaviourWithRepository { message, agent, mts -> agent.block(message, mts) }
        agent(name, capacity, lifecycle, behaviour)
    }

    operator fun get(agentId: String): Agent
}