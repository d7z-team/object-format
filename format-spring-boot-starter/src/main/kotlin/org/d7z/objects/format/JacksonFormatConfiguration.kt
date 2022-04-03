package org.d7z.objects.format

import com.fasterxml.jackson.databind.ObjectMapper
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.ext.json.JacksonDataFormat
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(JacksonDataFormat::class, ObjectMapper::class)
@AutoConfigureAfter(JacksonAutoConfiguration::class)
open class JacksonFormatConfiguration {
    @Bean
    open fun jacksonFormat(objectMapper: ObjectMapper): IFormat {
        return JacksonDataFormat(objectMapper)
    }
}
