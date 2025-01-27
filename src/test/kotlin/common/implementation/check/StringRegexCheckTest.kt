package common.implementation.check

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StringRegexCheckTest {

    @Test
    fun test_check_true() {
        val equalCheck = StringRegexCheck(Regex("\\d"))

        val result = equalCheck.test("1")

        assertEquals(true, result)
    }

    @Test
    fun test_check_false() {
        val equalCheck = StringRegexCheck(Regex("\\d"))

        val result = equalCheck.test("sc")

        assertEquals(false, result)
    }
}