package common.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.time.Duration

class PropertiesUtilKtTest {

    @Test
    fun `parse should return list of AgentConfig objects`() {
        assertEquals(2, parse().size)
    }

}