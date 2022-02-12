package org.d7z.objects.format.api

/**
 * 通过 Java SPI 实例化 FormatContext
 *
 *  @property context IFormatContext 对象
 */
interface SpiFormatContext {
    val context: IFormatContext
}
