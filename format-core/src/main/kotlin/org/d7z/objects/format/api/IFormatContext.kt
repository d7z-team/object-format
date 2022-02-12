package org.d7z.objects.format.api

import java.io.Closeable
import kotlin.reflect.KClass

/**
 *  数据转换上下文
 */
interface IFormatContext : IDataCovert, Closeable {
    /**
     * 根据 class 对象获取对应的序列化实例
     *
     * @param type KClass<V>
     * @return IFormat<V>
     */
    fun <V : Any> getFormat(type: KClass<V>): IFormat

    /**
     * 获取上下文配置
     *
     * @param key String 配置名称
     * @param def String 配置默认值
     * @return String 配置值
     */
    fun getConfig(key: String, def: String): String

    /**
     * FormatContext 构造器
     */
    interface IContextBuilder {

        /**
         * 注册格式转换组件
         *
         * @param format IFormat 注册的组件
         * @return IContextBuilder 当前实例
         */
        fun register(format: IFormat): IContextBuilder

        /**
         * 增加自定义配置
         *
         * @param key String 配置名称
         * @param value String 配置内容
         * @return IContextBuilder 当前实例
         */
        fun addConfig(key: String, value: String): IContextBuilder

        /**
         * 创建 FormatContext 对象
         *
         * @return IFormatContext 创建的 Context 对象
         */
        fun build(): IFormatContext
    }
}
