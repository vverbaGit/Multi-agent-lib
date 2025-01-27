import common.interfaces.Agent
import common.interfaces.BehaviourWithRepository
import common.interfaces.DirectoryFacilitator
import common.interfaces.MessageTransportService
import common.message.Message
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.time.Duration

@ExperimentalCoroutinesApi
class DirectoryFacilitatorTest