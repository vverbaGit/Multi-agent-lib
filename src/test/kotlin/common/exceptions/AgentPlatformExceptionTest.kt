package common.exceptions

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class AgentPlatformExceptionTest {
    @Test
    fun exception() {
        assertThrows(UnrecognizedException::class.java) { throw UnrecognizedException() }
    }
}