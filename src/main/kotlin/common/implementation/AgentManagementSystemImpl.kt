@file:OptIn(DelicateCoroutinesApi::class)

package common.implementation

import common.interfaces.*
import common.message.Message
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.SelectClause2
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

/**
 * Agent management system impl
 *
 * @property messageTransportService
 * @property name
 * @property coroutineContext
 * @property capacity
 * @constructor Create empty Agent management system impl
 */
class AgentManagementSystemImpl(
    override val messageTransportService: MessageTransportService,
    override val name: String,
    override val coroutineContext: CoroutineContext,
    override val capacity: Int = Channel.CONFLATED
) : AgentManagementSystem {

    private var innerDFAgent: BaseDFAgent = BaseDFAgent.DFAgentImpl("MAIN", coroutineContext, capacity, this)

    init {
        launch { messageTransportService.collect { innerDFAgent.send(it) } }
    }

    override fun get(agentId: String): Agent = innerDFAgent[agentId]

    override suspend fun onPathUnresolved(message: Message) {
        println("Try send request to network")
    }

    override suspend fun df(
        name: String,
        mts: MessageTransportService?,
        capicity: Int,
        init: suspend DirectoryFacilitator.() -> Unit
    ) {
        innerDFAgent.df(name, mts, capicity, init)
    }

    override suspend fun agent(agent: Agent) {
        innerDFAgent.agent(agent)
    }

    override suspend fun agent(name: String, capacity: Int, lifecycle: Duration, behaviour: BehaviourWithRepository) {
        innerDFAgent.agent(name, capacity, lifecycle, behaviour)
    }

    override val identifier: String
        get() = name

    override val parent: DirectoryFacilitator
        get() = this


    override val isClosedForSend: Boolean
        get() = innerDFAgent.isClosedForSend

    override val onSend: SelectClause2<Message, SendChannel<Message>>
        get() = innerDFAgent.onSend

    override fun close(cause: Throwable?): Boolean = innerDFAgent.close()

    @ExperimentalCoroutinesApi
    override fun invokeOnClose(handler: (cause: Throwable?) -> Unit) = innerDFAgent.invokeOnClose(handler)

    override fun trySend(element: Message): ChannelResult<Unit> = innerDFAgent.trySend(element)

    override suspend fun send(element: Message) = innerDFAgent.send(element)

    /**
     * Register
     *
     */
    suspend fun register() {
        send(Message(innerDFAgent.identifier, innerDFAgent.identifier, code = AgentPlatform.CODE_REGISTRATION))
    }

    /**
     * Set inner d f agent
     *
     * @param agent
     */
    fun setInnerDFAgent(agent: BaseDFAgent) {
        this.innerDFAgent = agent
    }
}
