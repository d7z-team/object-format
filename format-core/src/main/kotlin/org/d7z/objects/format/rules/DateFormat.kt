package org.d7z.objects.format.rules

import org.d7z.objects.format.CovertException
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.api.IFormatContext
import java.util.Date
import kotlin.reflect.KClass

class DateFormat : IFormat {
    override val types = setOf(Date::class)

    override fun <T : Any> format(data: T, type: KClass<out T>, context: IFormatContext): String {
        if (type == Date::class && data is Date) {
            return data.time.toString()
        }
        throw CovertException("无法执行数据转换.")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> reduce(data: String, type: KClass<T>, context: IFormatContext): T {
        if (type == Date::class) {
            return Date(data.toLong()) as T
        }
        throw CovertException("无法执行数据转换.")
    }
}
