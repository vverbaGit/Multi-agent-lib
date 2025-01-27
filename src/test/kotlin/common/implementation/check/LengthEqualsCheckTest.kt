package common.implementation.check

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LengthEqualsCheckTest {

    @Test
    fun test_check_true() {
        val equalCheck = LengthEqualsCheck(1)

        val result = equalCheck.test("1")

        assertEquals(true, result)
    }

    @Test
    fun test_check_false() {
        val equalCheck = LengthEqualsCheck(1)

        val result = equalCheck.test("22")

        assertEquals(false, result)
    }
}