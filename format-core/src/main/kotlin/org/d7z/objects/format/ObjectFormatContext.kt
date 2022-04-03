package org.d7z.objects.format

import org.d7z.objects.format.api.FormatOverride
import org.d7z.objects.format.api.IDataCovert
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.api.IFormatContext
import org.d7z.objects.format.rules.BasicDataFormat
import org.d7z.objects.format.rules.DateFormat
import org.d7z.objects.format.rules.DateTimeFormat
import org.d7z.objects.format.rules.DefaultDataFormat
import org.d7z.objects.format.rules.StringDataFormat
import java.util.ServiceLoader
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 *  Object Format 构造器实现
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

    /**
     * Object Format 构造器实现
     *
     * @property includeDefault Boolean 导入默认的实现
     * @property includeServiceLoader Boolean 从 ServiceLoader 下导入
     * @constructor
     */
    class Builder(
        private val includeDefault: Boolean = true,
        private val includeServiceLoader: Boolean = false,
    ) : IFormatContext.IContextBuilder {
        private val formatMap = HashMap<KClass<*>, IFormat>()
        private val configMap = HashMap<String, String>()

        init {
            initData()
        }

        /**
         * 初始化内部实例
         */
        private fun initData() {
            if (includeDefault) {
                register(BasicDataFormat())
                register(DateFormat())
                register(DateTimeFormat())
                register(StringDataFormat())
                register(DefaultDataFormat())
            }
            if (includeServiceLoader) {
                for (iFormat in spiFormatLoader()) {
                    register(iFormat)
                }
            }
        }

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
                initData()
                this.init()
            }
        }

        /**
         * 使用 ServiceLoader 加载符合规则的 IFormat 对象
         */
        private fun spiFormatLoader(): MutableList<IFormat> {
            val loader = ServiceLoader.load(IFormat::class.java)
            val formats = loader.toMutableList()
            formats.sortWith { o1, o2 -> // 对覆盖进行排序
                val o1Ann = o1::class.findAnnotation<FormatOverride>()?.over?.toSet()
                val o2Ann = o2::class.findAnnotation<FormatOverride>()?.over?.toSet()
                if (o1Ann != null && o1Ann.contains(o2::class)) {
                    1
                } else if (o1Ann != null) {
                    0
                } else if (o2Ann != null && o2Ann.contains(o1::class)) {
                    -1
                } else {
                    0
                }
            }
            return formats
        }
    }

    private fun init() {
        for (value in this.format.values) {
            value.init(this)
        }
    }
}
