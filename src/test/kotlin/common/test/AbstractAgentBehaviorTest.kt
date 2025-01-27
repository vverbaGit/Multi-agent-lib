package common.test

import common.implementation.RepositoryImpl
import common.interfaces.*
import common.message.Message
import common.interfaces.DirectoryFacilitator
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AbstractAgentBehaviorTest {

    private lateinit var behavior: TestAgentBehavior
    private lateinit var repository: Repository<Message>
    private lateinit var mockDirectoryFacilitator: DirectoryFacilitator
    private lateinit var mockAgent: Agent

    @BeforeEach
    fun setup() {
        repository = RepositoryImpl()
        behavior = TestAgentBehavior()
        mockDirectoryFacilitator = mockk<DirectoryFacilitator>()
        mockAgent = mockk<Agent>()
    }

    @Test
    fun `onReceive with CODE_CHECK and valid data should send handshake message`() {
        val message = Message("senderId", "receiverId", "data", AbstractAgentBehavior.CODE_CHECK)
        coEvery { mockDirectoryFacilitator.send(any()) } just Runs
        coEvery { mockAgent.identifier } returns "1"
        runBlocking {
            behavior.onReceive(message, mockAgent, mockDirectoryFacilitator)
        }
        assertEquals(AbstractAgentBehavior.CODE_CHECK, message.code)
    }

    @Test
    fun `onReceive with CODE_CHECK and invalid data should send error message and check message to the endpoint`() {
        val message = Message("senderId", "receiverId", "invalidData", AbstractAgentBehavior.CODE_CHECK)
        coEvery { mockDirectoryFacilitator.send(any()) } just Runs
        coEvery { mockAgent.identifier } returns "1"
        runBlocking {
            behavior.onReceive(message, mockAgent, mockDirectoryFacilitator)
        }
        assertEquals(AbstractAgentBehavior.CODE_CHECK, message.code)
    }

    @Test
    fun `onReceive with CODE_HAND_SHAKE and valid data should send submit handshake message`() {
        val message = Message("senderId", "receiverId", "data", AbstractAgentBehavior.CODE_HAND_SHAKE)
        coEvery { mockDirectoryFacilitator.send(any()) } just Runs
        coEvery { mockAgent.identifier } returns "1"
        runBlocking {
            behavior.onReceive(message, mockAgent, mockDirectoryFacilitator)
        }
        assertEquals(AbstractAgentBehavior.CODE_HAND_SHAKE, message.code)
    }

    @Test
    fun `onReceive with CODE_HAND_SHAKE and invalid data should send error message and check message to the endpoint`() {
        val message = Message("senderId", "receiverId", "invalidData", AbstractAgentBehavior.CODE_HAND_SHAKE)
        coEvery { mockDirectoryFacilitator.send(any()) } just Runs
        coEvery { mockAgent.identifier } returns "1"
        runBlocking {
            behavior.onReceive(message, mockAgent, mockDirectoryFacilitator)
        }
        assertEquals(AbstractAgentBehavior.CODE_HAND_SHAKE, message.code)
    }

    @Test
    fun `onReceive with CODE_SUBMIT_HAND_SHAKE should add senderId to appropriateIds and send submit handshake message`() {
        val message = Message("senderId", "receiverId", "data", AbstractAgentBehavior.CODE_SUBMIT_HAND_SHAKE)
        coEvery { mockDirectoryFacilitator.send(any()) } just Runs
        coEvery { mockAgent.identifier } returns "1"
        runBlocking {
            behavior.onReceive(message, mockAgent, mockDirectoryFacilitator)
        }
        assertEquals(AbstractAgentBehavior.CODE_SUBMIT_HAND_SHAKE, message.code)
    }

    @Test
    fun `onReceive with CODE_ERROR should add senderId to inappropriateIds and send error message and check message to the endpoint`() {
        val message = Message("senderId", "receiverId", code = AbstractAgentBehavior.CODE_ERROR)
        coEvery { mockDirectoryFacilitator.send(any()) } just Runs
        coEvery { mockAgent.identifier } returns "1"
        runBlocking {
            behavior.onReceive(message, mockAgent, mockDirectoryFacilitator)
        }
        assertEquals(AbstractAgentBehavior.CODE_ERROR, message.code)
    }

    private class TestAgentBehavior() :
        AbstractAgentBehavior<String>("data", mutableListOf()) {
        override fun endpoint(mts: DirectoryFacilitator): String {
            return "endpoint"
        }

    }
}