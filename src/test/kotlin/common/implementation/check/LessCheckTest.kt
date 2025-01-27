package common.implementation.check

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LessCheckTest {

    @Test
    fun test_check_true() {
        val equalCheck = LessCheck(1)

        val result = equalCheck.test(0)

        assertEquals(true, result)
    }

    @Test
    fun test_check_false() {
        val equalCheck = LessCheck(1)

        val result = equalCheck.test(1)

        assertEquals(false, result)
    }
}