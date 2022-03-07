package org.d7z.objects.format.rules

import org.d7z.objects.format.utils.objectFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Date

internal class DateFormatTest {
    @Test
    fun test() {
        val data = Date()
        objectFormat().apply {
            assertEquals(reduce(format(data), Date::class), data)
        }
    }
}
