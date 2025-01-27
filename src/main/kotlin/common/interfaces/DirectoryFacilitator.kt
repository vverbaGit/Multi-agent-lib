package common.interfaces

import common.message.Message
import kotlinx.coroutines.channels.Channel
import kotlinx.datetime.Clock
import kotlin.time.Duration

/**
 * A Directory Facilitator is an optional component of the AP, but if it is present,
 * it must be implemented as a DF service.
 * The DF provides yellow pages services to other agents.
 * Agents may register their services with the DF or query the DF to find out what services are offered by other agents.
 * Multiple DFs may exist within an AP and may be federated.
 *
 */
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
        lifecycle: Duration = Duration.INFINITE,
        behaviour: BehaviourWithRepository
    )

    suspend fun agent(
        name: String = "${Clock.System.now().toEpochMilliseconds()}",
        capacity: Int = Channel.CONFLATED,
        lifecycle: Duration = Duration.INFINITE,
        block: suspend Agent.(Message, DirectoryFacilitator) -> Unit
    ) {
        val behaviour = BehaviourWithRepository { message, agent, mts -> agent.block(message, mts) }
        agent(name, capacity, lifecycle, behaviour)
    }

    operator fun get(agentId: String): Agent
}