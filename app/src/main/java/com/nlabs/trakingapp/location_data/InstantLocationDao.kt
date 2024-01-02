package com.nlabs.trakingapp.location_data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InstantLocationDao {

    @Upsert
    suspend fun upsertLocation(instantLocation: InstantLocation)

    @Delete
    suspend fun deleteLocation(instantLocation: InstantLocation)

    @Query("SELECT * FROM instantLocation_table ORDER BY id ASC")
    fun getData(): LiveData<List<InstantLocation>>
}