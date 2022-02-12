package org.d7z.objects.format.rules

import org.d7z.objects.format.CovertException
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.api.IFormatContext
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

/**
 * 基础数据类型转换
 */
class BasicDataFormat : IFormat {
    override val types = setOf(
        Byte::class, Short::class,
        Int::class, Long::class,
        Float::class, Double::class,
        Boolean::class, Char::class
    )

    override fun <T : Any> format(data: T, type: KClass<out T>, context: IFormatContext): String {
        if (type.isSuperclassOf(data::class).not()) {
            throw CovertException("$type 不是 ${data::class} 的关联类型.")
        }
        return when (type) {
            Byte::class -> (data as Byte).toString()
            Short::class -> (data as Short).toString()
            Int::class -> (data as Int).toString()
            Long::class -> (data as Long).toString()
            Float::class -> (data as Float).toString()
            Double::class -> (data as Double).toString()
            Boolean::class -> (data as Boolean).toString()
            Char::class -> (data as Char).toString()
            else -> throw CovertException("$type 不受支持.")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> reduce(data: String, type: KClass<T>, context: IFormatContext): T {
        return when (type) {
            Byte::class -> data.toByteOrNull()
            Short::class -> data.toShortOrNull()
            Int::class -> data.toIntOrNull()
            Long::class -> data.toLongOrNull()
            Float::class -> data.toFloatOrNull()
            Double::class -> data.toDoubleOrNull()
            Boolean::class -> data.toByteOrNull()
            Char::class -> if (data.isEmpty()) {
                null
            } else {
                data[0]
            }
            else -> throw CovertException("$type 不受支持.")
        } as T? ?: throw CovertException("数据 $data 无法转换成 $type 类型.")
    }
}
