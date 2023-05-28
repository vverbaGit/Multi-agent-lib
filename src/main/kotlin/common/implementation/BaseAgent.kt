package common.implementation

import common.interfaces.Agent
import common.interfaces.Behaviour
import common.interfaces.BehaviourWithRepository
import common.interfaces.DirectoryFacilitator
import common.message.Message
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.selects.SelectClause2
import kotlin.coroutines.CoroutineContext

/**
 * Base agent
 *
 * @property name
 * @property behaviour
 * @property coroutineContext
 * @property capacity
 * @property parent
 * @constructor Create empty Base agent
 */
abstract class BaseAgent internal constructor(
    override val name: String,
    private val behaviour: BehaviourWithRepository,
    final override val coroutineContext: CoroutineContext,
    final override val capacity: Int = Channel.CONFLATED,
    override val parent: DirectoryFacilitator
) : Agent, Behaviour by behaviour {

    @OptIn(ObsoleteCoroutinesApi::class)
    private val agent = actor<Message>(
        context = coroutineContext,
        capacity = capacity
    ) {
        for (message in channel) {
            behaviour.onReceiveWithRepo(message, this@BaseAgent, parent)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @ExperimentalCoroutinesApi
    override val isClosedForSend: Boolean
        get() = agent.isClosedForSend

    override val onSend: SelectClause2<Message, SendChannel<Message>>
        get() = agent.onSend

    override fun close(cause: Throwable?): Boolean = agent.close(cause)

    @ExperimentalCoroutinesApi
    override fun invokeOnClose(handler: (cause: Throwable?) -> Unit) = agent.invokeOnClose(handler)

    override fun trySend(element: Message): ChannelResult<Unit> = agent.trySend(element)

    override suspend fun send(element: Message) = agent.send(element)

    override val identifier: String
        get() = "${parent.identifier}@$name"

    /**
     * Agent impl
     *
     * @constructor
     *
     * @param name
     * @param behaviour
     * @param coroutineContext
     * @param capacity
     * @param parent
     */
    internal class AgentImpl(
        name: String,
        behaviour: BehaviourWithRepository,
        coroutineContext: CoroutineContext,
        capacity: Int = Channel.CONFLATED,
        parent: DirectoryFacilitator
    ) : BaseAgent(name, behaviour, coroutineContext, capacity, parent)
}