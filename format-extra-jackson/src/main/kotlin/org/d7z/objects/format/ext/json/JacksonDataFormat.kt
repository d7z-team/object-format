package org.d7z.objects.format.ext.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.d7z.objects.format.api.FormatOverride
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.api.IFormatContext
import org.d7z.objects.format.rules.DefaultDataFormat
import kotlin.reflect.KClass

@FormatOverride([DefaultDataFormat::class])
class JacksonDataFormat @JvmOverloads constructor(
    private val mapper: ObjectMapper = ObjectMapper().registerKotlinModule(),
) : IFormat {
    override val types = setOf(Any::class)

    override fun <T : Any> format(data: T, type: KClass<out T>, context: IFormatContext): String {
        return mapper.writeValueAsString(data)
    }

    override fun <T : Any> reduce(data: String, type: KClass<T>, context: IFormatContext): T {
        return mapper.readValue(data, type.java)
    }
}
