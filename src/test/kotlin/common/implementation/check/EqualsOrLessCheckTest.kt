package common.implementation.check

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EqualsOrLessCheckTest {

    @Test
    fun test_check_true() {
        val equalCheck = EqualsOrLessCheck(1)

        val result = equalCheck.test(1)

        assertEquals(true, result)
    }

    @Test
    fun test_check_false() {
        val equalCheck = EqualsOrLessCheck(1)

        val result = equalCheck.test(2)

        assertEquals(false, result)
    }
}