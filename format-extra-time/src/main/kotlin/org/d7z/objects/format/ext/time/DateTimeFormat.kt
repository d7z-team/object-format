package org.d7z.objects.format.ext.time

import org.d7z.objects.format.CovertException
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.api.IFormatContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class DateTimeFormat : IFormat {
    private lateinit var dateTimeFormat: DateTimeFormatter
    private lateinit var dateFormat: DateTimeFormatter
    override val types = setOf(LocalDate::class, LocalDateTime::class)

    override fun init(context: IFormatContext) {
        this.dateFormat = DateTimeFormatter.ofPattern(context.getConfig("format.pattern.date", "yyyy/MM/dd"))
        this.dateTimeFormat = DateTimeFormatter
            .ofPattern(context.getConfig("format.pattern.dateTime", "yyyy/MM/dd HH:mm:ss.SSSSSS"))
    }

    override fun <T : Any> format(data: T, type: KClass<out T>, context: IFormatContext): String {
        return when (type) {
            LocalDate::class -> dateFormat.format(data as LocalDate)
            LocalDateTime::class -> dateTimeFormat.format(data as LocalDateTime)
            else -> throw CovertException("不支持的数据类型.")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> reduce(data: String, type: KClass<T>, context: IFormatContext): T {
        return when (type) {
            LocalDate::class -> LocalDate.parse(data, dateFormat)
            LocalDateTime::class -> LocalDateTime.parse(data, dateTimeFormat)
            else -> throw CovertException("不支持的数据类型.")
        } as T
    }
}
