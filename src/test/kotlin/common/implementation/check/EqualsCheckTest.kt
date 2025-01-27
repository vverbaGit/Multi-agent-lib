package common.implementation.check

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EqualsCheckTest {

    @Test
    fun test_check_true() {
        val equalCheck = EqualsCheck(1)

        val result = equalCheck.test(1)

        assertEquals(true, result)
    }

    @Test
    fun test_check_false() {
        val equalCheck = EqualsCheck(1)

        val result = equalCheck.test(2)

        assertEquals(false, result)
    }
}