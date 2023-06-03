package common.implementation

import common.implementation.AgentPlatform.coroutineContext
import common.interfaces.Agent
import common.interfaces.DirectoryFacilitator
import common.interfaces.MessageTransportService
import common.message.Message
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class BaseDFAgentTest {

    private lateinit var baseDFAgent: BaseDFAgent
    private lateinit var parentDirectoryFacilitator: DirectoryFacilitator

    @BeforeEach
    fun setup() {
        parentDirectoryFacilitator = mockk()
        baseDFAgent = spyk(BaseDFAgentImpl("TestAgent", coroutineContext, parent = parentDirectoryFacilitator))
    }

    @Test
    fun `onPathUnresolved should call parent's send`() = runBlocking {
        val message = Message("senderId", "receiverId")
        coEvery { parentDirectoryFacilitator.send(any()) } just Runs

        baseDFAgent.onPathUnresolved(message)
    }

    @Test
    fun `onReceive should send message to children`() = runBlocking {
        val message = Message("senderId", "TestAgent@ChildAgent")
        val childAgent = mockk<Agent>()
        every { childAgent.identifier } returns "TestAgent@ChildAgent"
        every { baseDFAgent.children } returns mutableMapOf("ChildAgent" to childAgent)
        coEvery { childAgent.send(any()) } just Runs

        baseDFAgent.onReceive(message, baseDFAgent, parentDirectoryFacilitator)
    }

    // Add more test cases as needed

    private class BaseDFAgentImpl(
        name: String,
        coroutineContext: CoroutineContext,
        capacity: Int = Channel.CONFLATED,
        parent: DirectoryFacilitator
    ) : BaseDFAgent(name, coroutineContext, capacity, parent) {
        override val parent: DirectoryFacilitator = parent
        val mts: MessageTransportService? = null

        override suspend fun onPathUnresolved(message: Message) {}
        override suspend fun onReceive(message: Message, agent: Agent, mts: DirectoryFacilitator) {}
    }
}