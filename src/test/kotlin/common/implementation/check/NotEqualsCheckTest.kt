package common.implementation.check

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NotEqualsCheckTest {

    @Test
    fun test_check_true() {
        val equalCheck = NotEqualsCheck(2)

        val result = equalCheck.test(1)

        assertEquals(true, result)
    }

    @Test
    fun test_check_false() {
        val equalCheck = NotEqualsCheck(1)

        val result = equalCheck.test(1)

        assertEquals(false, result)
    }
}