package common.test

import common.implementation.AgentPlatform
import common.implementation.RepositoryImpl
import common.interfaces.Agent
import common.interfaces.DirectoryFacilitator
import common.interfaces.Repository
import common.message.Message
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ConsumerAgentBehaviourTest {

    private lateinit var behavior: ConsumerAgentBehaviour<String>
    private lateinit var repository: Repository<Message>
    private lateinit var mockDirectoryFacilitator: DirectoryFacilitator
    private lateinit var mockAgent: Agent

    @BeforeEach
    fun setup() {
        repository = RepositoryImpl()
        behavior = ConsumerAgentBehaviour("data")
        mockDirectoryFacilitator = mockk<DirectoryFacilitator>()
        mockAgent = mockk<Agent>()
    }

    @Test
    fun `onReceive with CODE_SUBMIT_HAND_SHAKE should add senderId to appropriateIds and send submit handshake message`() {
        val message = Message("senderId", "receiverId", "data", AbstractAgentBehavior.CODE_SUBMIT_HAND_SHAKE)
        coEvery { mockDirectoryFacilitator.send(any()) } just Runs
        coEvery { mockAgent.identifier } returns "1"
        coEvery { mockDirectoryFacilitator.parent.identifier } returns "2"
        runBlocking {
            behavior.onReceive(message, mockAgent, mockDirectoryFacilitator)
        }
        assertEquals(AbstractAgentBehavior.CODE_SUBMIT_HAND_SHAKE, message.code)
    }

    @Test
    fun `onReceive with CODE_REGISTRATION should add senderId to appropriateIds and send submit handshake message`() {
        val message = Message("senderId", "receiverId", "data", AgentPlatform.CODE_REGISTRATION)
        coEvery { mockDirectoryFacilitator.send(any()) } just Runs
        coEvery { mockAgent.identifier } returns "1"
        coEvery { mockDirectoryFacilitator.parent.identifier } returns "2"
        runBlocking {
            behavior.onReceive(message, mockAgent, mockDirectoryFacilitator)
        }
        assertEquals(AgentPlatform.CODE_REGISTRATION, message.code)
    }

    @Test
    fun `onReceive with CODE_ERROR should add senderId to inappropriateIds and send error message and check message to the endpoint`() {
        val message = Message("senderId", "receiverId", code = AbstractAgentBehavior.CODE_ERROR)
        coEvery { mockDirectoryFacilitator.send(any()) } just Runs
        coEvery { mockAgent.identifier } returns "1"
        coEvery { mockDirectoryFacilitator.parent.identifier } returns "2"
        runBlocking {
            behavior.onReceive(message, mockAgent, mockDirectoryFacilitator)
        }
        assertEquals(AbstractAgentBehavior.CODE_ERROR, message.code)
    }
}