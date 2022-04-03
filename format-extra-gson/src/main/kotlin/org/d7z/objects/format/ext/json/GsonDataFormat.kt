package org.d7z.objects.format.ext.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.d7z.objects.format.api.FormatOverride
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.api.IFormatContext
import org.d7z.objects.format.rules.DefaultDataFormat
import kotlin.reflect.KClass

@FormatOverride([DefaultDataFormat::class])
class GsonDataFormat @JvmOverloads constructor(
    private val gson: Gson = GsonBuilder().create(),
) : IFormat {
    override val types = setOf(Any::class)

    override fun <T : Any> format(data: T, type: KClass<out T>, context: IFormatContext): String {
        return gson.toJson(data, type.javaObjectType)
    }

    override fun <T : Any> reduce(data: String, type: KClass<T>, context: IFormatContext): T {
        return gson.fromJson(data, type.javaObjectType)
    }
}
