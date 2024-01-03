package com.nlabs.trakingapp.data.location

import androidx.room.TypeConverter
import java.time.Instant

/**
 * Static class with conversion methods
 * to be used with Room databases
 * */
class Converters {

    @TypeConverter
    fun instantToLong(timestamp: Instant?) = timestamp?.toEpochMilli()

    @TypeConverter
    fun longToInstant(timestamp: Long?) = timestamp?.let { Instant.ofEpochMilli(it) }

}