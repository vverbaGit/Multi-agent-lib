import common.implementation.AgentManagementSystemImpl
import common.implementation.AgentPlatform
import common.implementation.BaseDFAgent
import common.interfaces.Agent
import common.interfaces.BehaviourWithRepository
import common.interfaces.DirectoryFacilitator
import common.interfaces.MessageTransportService
import common.message.Message
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.selects.SelectClause2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalCoroutinesApi
@ExperimentalTime
class AgentManagementSystemImplTest {

    private lateinit var messageTransportService: MessageTransportService
    private lateinit var df: DirectoryFacilitator
    private lateinit var agentManagementSystem: AgentManagementSystemImpl

    @BeforeEach
    fun setUp() {
        messageTransportService = object : MessageTransportService {
            private val messageFlow = MutableSharedFlow<Message>()

            fun send(message: Message) {
                CoroutineScope(EmptyCoroutineContext).launch {
                    messageFlow.emit(message)
                }
            }

            override val replayCache: List<Message>
                get() = mutableListOf()

            override suspend fun collect(collector: FlowCollector<Message>): Nothing {
                messageFlow.collect(collector)
            }
        }

        agentManagementSystem = AgentManagementSystemImpl(messageTransportService, "TestAgent", EmptyCoroutineContext)
    }

    @Test
    fun `register() sends registration message`() {
        val sentMessage = Message("senderId", "receiverId")

        messageTransportService = object : MessageTransportService {
            private val messageFlow = MutableSharedFlow<Message>()

            fun send(message: Message) {
                CoroutineScope(EmptyCoroutineContext).launch {
                    messageFlow.emit(message)
                }
            }

            override val replayCache: List<Message>
                get() = mutableListOf()

            override suspend fun collect(collector: FlowCollector<Message>): Nothing {
                println("sad")
                messageFlow.collect(collector)
            }
        }

        agentManagementSystem = AgentManagementSystemImpl(messageTransportService, "TestAgent", EmptyCoroutineContext)

        runBlocking { agentManagementSystem.register() }

        assertNotNull(sentMessage)
    }


    @Test
    fun `registers() sends registration message`() {
        val sentMessage = Message("senderId", "receiverId")

        messageTransportService = object : MessageTransportService {
            private val messageFlow = MutableSharedFlow<Message>()

            fun send(message: Message) {
                CoroutineScope(EmptyCoroutineContext).launch {
                    messageFlow.emit(message)
                }
            }

            override val replayCache: List<Message>
                get() = mutableListOf()

            override suspend fun collect(collector: FlowCollector<Message>): Nothing {
                println("sad")
                messageFlow.collect(collector)
            }
        }

        df = object : DirectoryFacilitator {
            override suspend fun onPathUnresolved(message: Message) {
                TODO("Not yet implemented")
            }

            override suspend fun df(
                name: String,
                mts: MessageTransportService?,
                capicity: Int,
                init: suspend DirectoryFacilitator.() -> Unit
            ) {
                TODO("Not yet implemented")
            }

            override suspend fun agent(agent: Agent) {
                TODO("Not yet implemented")
            }

            override suspend fun agent(
                name: String,
                capacity: Int,
                lifecycle: Duration,
                behaviour: BehaviourWithRepository
            ) {
                TODO("Not yet implemented")
            }

            override fun get(agentId: String): Agent {
                TODO("Not yet implemented")
            }

            override val name: String
                get() = TODO("Not yet implemented")
            override val identifier: String
                get() = TODO("Not yet implemented")
            override val capacity: Int
                get() = TODO("Not yet implemented")
            override val parent: DirectoryFacilitator
                get() = TODO("Not yet implemented")

            @DelicateCoroutinesApi
            override val isClosedForSend: Boolean
                get() = TODO("Not yet implemented")
            override val onSend: SelectClause2<Message, SendChannel<Message>>
                get() = TODO("Not yet implemented")

            override fun close(cause: Throwable?): Boolean {
                TODO("Not yet implemented")
            }

            override fun invokeOnClose(handler: (cause: Throwable?) -> Unit) {
                TODO("Not yet implemented")
            }

            override suspend fun send(element: Message) {
                TODO("Not yet implemented")
            }

            override fun trySend(element: Message): ChannelResult<Unit> {
                TODO("Not yet implemented")
            }

            override val coroutineContext: CoroutineContext
                get() = TODO("Not yet implemented")

        }

        agentManagementSystem = AgentManagementSystemImpl(messageTransportService, "TestAgent", EmptyCoroutineContext)

        agentManagementSystem.setInnerDFAgent(BaseDFAgent.DFAgentImpl("name", EmptyCoroutineContext, Channel.CONFLATED, df))
    }
}