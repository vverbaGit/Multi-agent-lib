package common.implementation

import common.exceptions.UnrecognizedException
import common.interfaces.*
import common.message.Message
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.SelectClause2
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

/**
 * Base d f agent
 *
 * @property name
 * @property coroutineContext
 * @property capacity
 * @property parent
 * @constructor Create empty Base d f agent
 */
abstract class BaseDFAgent internal constructor(
    override val name: String,
    final override val coroutineContext: CoroutineContext,
    final override val capacity: Int = Channel.CONFLATED,
    override val parent: DirectoryFacilitator
) : DirectoryFacilitator, BehaviourWithRepository {

    @OptIn(ObsoleteCoroutinesApi::class)
    protected val agent = actor<Message>(
        context = coroutineContext,
        capacity = capacity
    ) {
        for (message in channel) with(this@BaseDFAgent) {
            onReceiveWithRepo(message, this, parent)
        }
    }

    private val children = mutableMapOf<String, Agent>()

    override val identifier: String
        get() = "${parent.identifier}@$name"

    override suspend fun agent(agent: Agent) {
        children[agent.name] = agent
    }

    override suspend fun agent(name: String, capacity: Int, lifecycle: Duration, behaviour: BehaviourWithRepository) {
        val newAgent = BaseAgent.AgentImpl(name, behaviour, coroutineContext + Job(), this.capacity, this)
        agent(newAgent)
    }

    @DelicateCoroutinesApi
    override val isClosedForSend: Boolean
        get() = agent.isClosedForSend


    override val onSend: SelectClause2<Message, SendChannel<Message>>
        get() = agent.onSend

    override fun close(cause: Throwable?): Boolean = agent.close(cause)

    override fun invokeOnClose(handler: (cause: Throwable?) -> Unit) = agent.invokeOnClose(handler)

    override fun trySend(element: Message): ChannelResult<Unit> = agent.trySend(element)

    override suspend fun send(element: Message) = agent.send(element)

    override suspend fun df(
        name: String,
        mts: MessageTransportService?,
        capicity: Int,
        init: suspend DirectoryFacilitator.() -> Unit
    ) {
        val newDf = DFAgentImpl(name, coroutineContext + Job(), capacity, this)
        newDf.init()
        agent(newDf)
    }

    override suspend fun onReceive(message: Message, agent: Agent, mts: DirectoryFacilitator) =
        withContext(coroutineContext) {
            val receiverPath = message.receiverId.split("@")
            when (val currentDFIndex = receiverPath.indexOfFirst { it == name }) {
                receiverPath.lastIndex -> with(message) {
                    children.values.forEach { it.send(Message(senderId, it.identifier, data, code, timestamp)) }
                }
                receiverPath.lastIndex - 1 -> {
                    val child = children[receiverPath.last()]
                    child?.sendMessage(message) ?: throw UnrecognizedException()
                }
                -1 -> {
                    onPathUnresolved(message)
                }
                else -> children[receiverPath[currentDFIndex + 1]]?.sendMessage(message) ?: throw UnrecognizedException()
            }
        }

    override suspend fun onPathUnresolved(message: Message) = parent.send(message)

    override fun get(agentId: String): Agent = children[agentId]!!

    private fun Agent.sendMessage(message: Message) {
        trySend(message).getOrNull()
    }

    /**
     * D f agent impl
     *
     * @constructor
     *
     * @param name
     * @param coroutineContext
     * @param capacity
     * @param parent
     */
    internal class DFAgentImpl(
        name: String,
        coroutineContext: CoroutineContext,
        capacity: Int = Channel.CONFLATED,
        parent: DirectoryFacilitator
    ) : BaseDFAgent(name, coroutineContext, capacity, parent)
}

