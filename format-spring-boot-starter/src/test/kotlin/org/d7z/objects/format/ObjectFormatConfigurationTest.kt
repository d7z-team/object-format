package org.d7z.objects.format

import com.fasterxml.jackson.databind.ObjectMapper
import org.d7z.objects.format.ext.json.JacksonDataFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest(
    classes = [Application::class]
)
internal class ObjectFormatConfigurationTest {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun dataCovert() {
        val bean = applicationContext.getBean<ObjectFormatContext>(ObjectFormatContext::class)
        val data = TestData("tom", 12)
        assertEquals(objectMapper.readValue(bean.format(data), TestData::class.java), data)
        assertEquals(bean.getFormat(TestData::class)::class, JacksonDataFormat::class)
    }

    data class TestData(
        val name: String,
        val age: Int,
    )
}
