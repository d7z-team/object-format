package org.d7z.objects.format.rules

import org.d7z.objects.format.CovertException
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.api.IFormatContext
import kotlin.reflect.KClass

class StringDataFormat : IFormat {
    override val types = setOf(String::class, StringBuilder::class, StringBuffer::class)

    override fun <T : Any> format(data: T, type: KClass<out T>, context: IFormatContext): String {
        return when (type) {
            String::class, StringBuilder::class, StringBuffer::class -> data.toString()
            else -> throw CovertException("$type 不受支持.")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> reduce(data: String, type: KClass<T>, context: IFormatContext): T {
        return when (type) {
            String::class -> data
            StringBuffer::class -> StringBuffer(data)
            StringBuilder::class -> StringBuilder(data)
            else -> throw CovertException("$type 不受支持.")
        } as T
    }
}
