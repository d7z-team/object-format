package org.d7z.objects.format

import org.d7z.objects.format.api.FormatOverride
import org.d7z.objects.format.api.IDataCovert
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.api.IFormatContext
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 *
 */
class ObjectFormatContext private constructor(
    private val format: Map<KClass<*>, IFormat>,
    private val config: Map<String, String>,
) : IFormatContext, IDataCovert {

    override fun <V : Any> getFormat(type: KClass<V>): IFormat {
        return format.getOrElse(
            type
        ) {
            format[Any::class]
                ?: throw RuntimeException("无法找到默认的 Any 实现或此实例已被销毁.")
        }
    }

    override fun getConfig(key: String, def: String): String {
        return config.getOrDefault(key, def)
    }

    override fun close() {
        for (value in format.values) {
            value.close(this)
        }
        if (format is MutableMap<KClass<*>, IFormat>) {
            format.clear()
        }
        if (config is MutableMap<String, String>) {
            config.clear()
        }
    }

    override fun <T : Any> format(data: T, type: KClass<out T>): String {
        return try {
            getFormat(type).format(data, type, this)
        } catch (e: CovertException) {
            throw e
        } catch (e: Exception) {
            throw CovertException("数据转换异常.", e)
        }
    }

    override fun <T : Any> reduce(format: String, type: KClass<T>): T {
        return try {
            getFormat(type).reduce(format, type, this)
        } catch (e: CovertException) {
            throw e
        } catch (e: Exception) {
            throw CovertException("数据转换异常.", e)
        }
    }

    override fun verify(format: String, type: KClass<Any>): Boolean {
        return try {
            getFormat(type).verify(format, type, this)
        } catch (e: CovertException) {
            throw e
        } catch (e: Exception) {
            throw CovertException("数据转换异常.", e)
        }
    }

    class Builder : IFormatContext.IContextBuilder {
        private val formatMap = HashMap<KClass<*>, IFormat>()
        private val configMap = HashMap<String, String>()

        @Synchronized
        override fun register(format: IFormat): IFormatContext.IContextBuilder {
            val overrideClasses =
                (format::class.findAnnotation<FormatOverride>()?.over ?: arrayOf()).toSet()
            for (type in format.types) {
                val oldFormat = formatMap.putIfAbsent(type, format)
                if ((oldFormat != null) && overrideClasses.contains(oldFormat::class)) {
                    // 此数据已存在且被标记替换
                    formatMap[type] = format
                }
            }
            return this
        }

        override fun addConfig(key: String, value: String): IFormatContext.IContextBuilder {
            configMap[key] = value
            return this
        }

        override fun build(): ObjectFormatContext {
            return ObjectFormatContext(HashMap(formatMap), HashMap(configMap)).apply {
                formatMap.clear()
                configMap.clear()
                this.init()
            }
        }
    }

    private fun init() {
        for (value in this.format.values) {
            value.init(this)
        }
    }
}
