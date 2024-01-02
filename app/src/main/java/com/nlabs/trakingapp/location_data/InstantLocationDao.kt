package com.nlabs.trakingapp.location_data

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

    @Query("SELECT * FROM instantLocation_table ORDER BY id DESC")
    fun getData(): LiveData<List<InstantLocation>>
}