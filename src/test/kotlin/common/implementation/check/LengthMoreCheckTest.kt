package common.implementation.check

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LengthMoreCheckTest {

    @Test
    fun test_check_true() {
        val equalCheck = LengthMoreCheck(1)

        val result = equalCheck.test("22")

        assertEquals(true, result)
    }

    @Test
    fun test_check_false() {
        val equalCheck = LengthMoreCheck(1)

        val result = equalCheck.test("2")

        assertEquals(false, result)
    }
}