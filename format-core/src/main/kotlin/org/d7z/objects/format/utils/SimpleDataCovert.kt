package org.d7z.objects.format.utils

import org.d7z.objects.format.ObjectFormatContext
import org.d7z.objects.format.api.IDataCovert
import org.d7z.objects.format.api.SpiFormatContext
import java.util.ServiceLoader
import kotlin.reflect.KClass

/**
 *
 * 基于 Java SPI 机制的 object format 加载器
 */
open class SimpleDataCovert : IDataCovert {
    private val context by lazy {
        ServiceLoader.load(SpiFormatContext::class.java).findFirst().map { it.context }.orElseGet {
            ObjectFormatContext.Builder(includeDefault = true, includeServiceLoader = true).build()
        }
    }

    override fun <T : Any> format(data: T, type: KClass<out T>): String {
        return context.format(data, type)
    }

    override fun <T : Any> reduce(format: String, type: KClass<T>): T {
        return context.reduce(format, type)
    }

    override fun verify(format: String, type: KClass<Any>): Boolean {
        return context.verify(format, type)
    }

    override fun close() {
        context.close()
    }
}
