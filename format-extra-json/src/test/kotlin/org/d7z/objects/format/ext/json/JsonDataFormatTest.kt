package org.d7z.objects.format.ext.json

import com.google.gson.Gson
import org.d7z.objects.format.utils.objectFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class JsonDataFormatTest {
    @Test
    fun test() {
        val data = TestData(12, "dragon")
        val message = objectFormat().format(data)
        assertEquals(Gson().fromJson(message, TestData::class.java), data)
    }

    data class TestData(val age: Int, val name: String)
}
