package org.d7z.objects.format.rules

import org.d7z.objects.format.CovertException
import org.d7z.objects.format.ObjectFormatContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class BasicDataFormatTest {
    private val format = ObjectFormatContext.Builder().register(BasicDataFormat()).build()

    @Test
    fun format() {
        assertEquals(format.format(12), "12") // Int
        assertEquals(format.format(12L), "12") // Long
        assertEquals(format.format(12.000000001), "12.000000001") // Double
        assertEquals(format.format(false), "false") // Boolean
        assertThrows<CovertException> {
            format.format("", Char::class)
        }
    }

    @Test
    fun reduce() {
        assertEquals(format.reduce("12", Long::class), 12L)
        assertEquals(format.reduce("12", Double::class), 12.00)
        assertThrows<CovertException> {
            assertEquals(format.reduce("12.121", Int::class), 12)
        }
    }
}
