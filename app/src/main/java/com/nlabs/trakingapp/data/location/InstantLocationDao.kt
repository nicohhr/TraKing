package com.nlabs.trakingapp.data.location

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InstantLocationDao {

    @Upsert
    suspend fun upsertLocation(instantLocation: InstantLocation)

    @Delete
    suspend fun deleteLocation(instantLocation: InstantLocation)

    @Query("DELETE FROM instantLocation_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM instantLocation_table WHERE isFromRoute=0 ORDER BY creationInstant DESC")
    fun getLocationData(): LiveData<List<InstantLocation>>

    @Query("SELECT * FROM instantLocation_table WHERE creationInstant AND isFromRoute=1 BETWEEN :startTime AND :endTime")
    fun getRoute(startTime: Long, endTime: Long): LiveData<List<InstantLocation>>
}