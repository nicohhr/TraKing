package com.nlabs.trakingapp.data.route

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "routes_table")
data class Route(
    private var name: String = "New Route",
    @PrimaryKey
    val startTime: Long,
    var stopTime: Long,
    private var isRecording: Boolean = true
) {
    fun getStartTimeInstant(): Instant = startTime.let { Instant.ofEpochMilli(it) }
    fun getStopTimeInstant(): Instant = stopTime.let { Instant.ofEpochMilli(it) }
    fun getName(): String = name
    fun getShortKey(n: Int = 4): String = startTime.toString().takeLast(n)
    fun isRecording(): Boolean = isRecording
}
