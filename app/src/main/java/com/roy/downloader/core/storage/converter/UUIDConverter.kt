package com.roy.downloader.core.storage.converter

import androidx.room.TypeConverter
import java.util.UUID

object UUIDConverter {
    @JvmStatic
    @TypeConverter
    fun toUUID(uuidStr: String?): UUID? {
        if (uuidStr == null) return null
        val uuid: UUID = try {
            UUID.fromString(uuidStr)
        } catch (e: IllegalArgumentException) {
            return null
        }
        return uuid
    }

    @JvmStatic
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}
