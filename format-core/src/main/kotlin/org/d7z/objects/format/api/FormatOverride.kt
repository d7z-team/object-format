package org.d7z.objects.format.api

import kotlin.reflect.KClass

/**
 * 标记替代 IFormat
 *
 *
 * @property over Array<KClass<out IFormat>> 被替代的 Format
 * @constructor
 */
@Target(AnnotationTarget.CLASS)
annotation class FormatOverride(val over: Array<KClass<out IFormat>>)
