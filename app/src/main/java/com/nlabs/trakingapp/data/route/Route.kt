package com.nlabs.trakingapp.data.route

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Entity(tableName = "routes_table")
data class Route(
    private var name: String = "New Route",
    @PrimaryKey
    val startTime: Long = Instant.now().toEpochMilli(),
    var stopTime: Long = 0,
    private var isRecording: Boolean = true
) {
    fun getStartTimeInstant(): Instant = startTime.let { Instant.ofEpochMilli(it) }
    fun getStopTimeInstant(): Instant = stopTime.let { Instant.ofEpochMilli(it) }
    fun getName(): String = name
    fun getShortKey(n: Int = 4): String = startTime.toString().takeLast(n)
    fun isRecording(): Boolean = isRecording
    fun startTimeToString(): String = DateTimeFormatter.ofPattern("dd/MM/yy | HH:mm").format(
        LocalDateTime.ofInstant(startTime.let { Instant.ofEpochMilli(it) }, ZoneId.systemDefault()))
    fun stopTimeToString(): String = DateTimeFormatter.ofPattern("dd/MM/yy | HH:mm").format(
        LocalDateTime.ofInstant(stopTime.let { Instant.ofEpochMilli(it) }, ZoneId.systemDefault()))
}
