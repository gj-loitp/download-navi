package com.roy.downloader.core.storage.converter

import android.net.Uri
import androidx.room.TypeConverter

object UriConverter {
    @JvmStatic
    @TypeConverter
    fun toUri(uriStr: String): Uri {
        return Uri.parse(uriStr)
    }

    @JvmStatic
    @TypeConverter
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }
}
