package com.nlabs.trakingapp.data.route

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routeLocation_table")
data class Route(
    var name: String = "New Route",
    @PrimaryKey
    val startTime: Long,
    var stopTime: Long,
    var isRecording: Boolean = true
)
