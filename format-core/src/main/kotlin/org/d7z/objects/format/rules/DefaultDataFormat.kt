package org.d7z.objects.format.rules

import org.d7z.objects.format.CovertException
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.api.IFormatContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.NotSerializableException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Base64
import kotlin.reflect.KClass

/**
 * 默认的 object format 实现，此实现基于Java 反序列化机制
 */
class DefaultDataFormat : IFormat {
    override val types = setOf(Any::class)

    override fun <T : Any> format(data: T, type: KClass<out T>, context: IFormatContext): String {
        return try {
            val output = ByteArrayOutputStream()
            val objectInputStream = ObjectOutputStream(output)
            objectInputStream.writeObject(data)
            output.close()
            Base64.getEncoder().encodeToString(output.toByteArray())
        } catch (e: NotSerializableException) {
            throw CovertException("数据序列化异常,$type 未实现 Serializable 接口.", e)
        } catch (e: Exception) {
            throw CovertException("数据序列化异常.", e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> reduce(data: String, type: KClass<T>, context: IFormatContext): T {
        var objectInputStream: ObjectInputStream? = null
        return try {
            objectInputStream = ObjectInputStream(ByteArrayInputStream(Base64.getDecoder().decode(data)))
            objectInputStream.readObject() as T
        } catch (e: Exception) {
            throw CovertException("数据反序列化异常.", e)
        } finally {
            objectInputStream?.close()
        }
    }
}
