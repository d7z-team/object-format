package org.d7z.objects.format.api

import org.d7z.objects.format.CovertException
import kotlin.reflect.KClass

/**
 * 格式化抽象
 *
 * @property types Set<KClass<out Any>> 支持的类
 */
interface IFormat {
    val types: Set<KClass<out Any>>

    /**
     * 对象初始化
     * @param context IFormatContext 当前上下文
     */
    fun init(context: IFormatContext) {
    }

    /**
     *
     * 对象转字符串
     *
     * @param data T 原始对象
     * @param type KClass<T> 对象转换类型
     * @param context IFormatContext 当前上下文
     * @return String 目标数据
     */
    fun <T : Any> format(data: T, type: KClass<out T>, context: IFormatContext): String

    /**
     *
     * 字符串转对象
     *
     * @param data String 目标字符串
     * @param type KClass<T> 对象类型
     * @param context IFormatContext 当前上下文
     * @return T 转换后的对象
     */
    fun <T : Any> reduce(data: String, type: KClass<T>, context: IFormatContext): T

    /**
     *
     * 字符串合法性校验
     *
     * @param data String 目标字符串
     * @param type KClass<Any> 对象类型
     * @param context IFormatContext 当前上下文
     * @return Boolean
     */
    fun verify(data: String, type: KClass<Any>, context: IFormatContext): Boolean {
        return try {
            reduce(data, type, context)
            true
        } catch (e: CovertException) {
            false
        }
    }

    /**
     *
     * 释放当前实例
     *
     * @param context IFormatContext 当前上下文
     */
    fun close(context: IFormatContext) {
    }
}
