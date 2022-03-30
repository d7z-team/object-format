package org.d7z.objects.format

import org.d7z.objects.format.api.IDataCovert
import kotlin.reflect.KClass

/**
 * 基于 Java SPI 机制加载默认的格式化方案
 * @Deprecated 4.0
 */
@Deprecated("请使用 IDataCovert 替代")
object GlobalObjectFormat : IDataCovert {
    override fun <T : Any> format(data: T, type: KClass<out T>) = IDataCovert.format(data, type)

    override fun <T : Any> reduce(format: String, type: KClass<T>): T = IDataCovert.reduce(format, type)

    override fun verify(format: String, type: KClass<Any>): Boolean = IDataCovert.verify(format, type)

    override fun close() {
        IDataCovert.close()
    }
}
