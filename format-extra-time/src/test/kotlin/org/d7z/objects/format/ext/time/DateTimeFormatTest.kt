package org.d7z.objects.format.ext.time

import org.d7z.objects.format.utils.objectFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class DateTimeFormatTest {
    @Test
    fun test() {
        val data = LocalDateTime.now()
        assertEquals(data, objectFormat().reduce(objectFormat().format(data), LocalDateTime::class))
        val date = data.toLocalDate()
        assertEquals(date, objectFormat().reduce(objectFormat().format(date), LocalDate::class))
    }
}
