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
data class BaseLocation(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    @PrimaryKey
    val creationInstant: Long = Instant.now().toEpochMilli(),
):Parcelable {
    fun getLatLng(): LatLng = LatLng(latitude, longitude)
    fun getOnCreationInstant(): Instant = creationInstant.let { Instant.ofEpochMilli(it) }
    fun getShortKey(n: Int = 4): String = creationInstant.toString().takeLast(n)
}
