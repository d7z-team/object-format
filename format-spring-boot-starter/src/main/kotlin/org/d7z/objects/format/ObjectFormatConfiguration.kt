package org.d7z.objects.format

import org.d7z.objects.format.api.IDataCovert
import org.d7z.objects.format.api.IFormat
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@AutoConfigureAfter(JacksonFormatConfiguration::class)
open class ObjectFormatConfiguration(
    private val applicationContext: ApplicationContext,
    private val objectFormatList: List<IFormat>,
) {

    @Bean
    open fun objectFormat(): ObjectFormatContext {
        val builder = ObjectFormatContext.Builder(includeDefault = true, includeServiceLoader = false)
        objectFormatList.forEach { builder.register(it) }
        return builder.build()
    }

    @Bean
    open fun dataCovert(objectFormat: ObjectFormatContext): IDataCovert {
        return objectFormat
    }
}
