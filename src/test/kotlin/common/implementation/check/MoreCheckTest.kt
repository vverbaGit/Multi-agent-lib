package common.implementation.check

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MoreCheckTest {

    @Test
    fun test_check_true() {
        val equalCheck = MoreCheck(1)

        val result = equalCheck.test(2)

        assertEquals(true, result)
    }

    @Test
    fun test_check_false() {
        val equalCheck = MoreCheck(1)

        val result = equalCheck.test(1)

        assertEquals(false, result)
    }
}