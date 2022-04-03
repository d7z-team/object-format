package org.d7z.objects.format

import com.google.gson.Gson
import org.d7z.objects.format.api.IFormat
import org.d7z.objects.format.ext.json.GsonDataFormat
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(GsonDataFormat::class, Gson::class)
@AutoConfigureAfter(GsonAutoConfiguration::class)
open class GsonFormatConfiguration {

    @Bean
    open fun gsonFormat(gson: Gson): IFormat {
        return GsonDataFormat(gson)
    }
}
