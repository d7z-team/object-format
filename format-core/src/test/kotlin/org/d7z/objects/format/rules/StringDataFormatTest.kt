package org.d7z.objects.format.rules

// ktlint-disable no-wildcard-imports
import org.d7z.objects.format.utils.objectFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class StringDataFormatTest {
    @Test
    fun test() {
        assertEquals(objectFormat().format("String"), "String")
        assertEquals(objectFormat().format(StringBuilder("String")), "String")
        assertEquals(objectFormat().format(StringBuffer("String")), "String")
    }
}
