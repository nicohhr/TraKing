package com.nlabs.trakingapp.location_data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "instantLocation_table")
data class InstantLocation(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    fun getLatLng(): LatLng = LatLng(latitude, longitude)
}
