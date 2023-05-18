package common.implementation.check

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LengthLessCheckTest {

    @Test
    fun test_check_true() {
        val equalCheck = LengthLessCheck(1)

        val result = equalCheck.test("")

        assertEquals(true, result)
    }

    @Test
    fun test_check_false() {
        val equalCheck = LengthLessCheck(1)

        val result = equalCheck.test("2")

        assertEquals(false, result)
    }
}