package org.d7z.objects.format.utils

import org.d7z.objects.format.api.IDataCovert

inline fun <reified T : Any> IDataCovert.reduce(data: String): T {
    return this.reduce(data, T::class)
}
