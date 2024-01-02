package com.nlabs.trakingapp.location_data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
@Entity(tableName = "instantLocation_table")
@TypeConverters(Converters::class)
data class InstantLocation(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val creationInstant: Instant = Instant.now(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
):Parcelable {
    fun getLatLng(): LatLng = LatLng(latitude, longitude)
}
