package com.nlabs.trakingapp.data.base_location

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BaseLocationDao {

    @Upsert
    suspend fun upsertLocation(baseLocation: BaseLocation)

    @Delete
    suspend fun deleteLocation(baseLocation: BaseLocation)

    @Query("DELETE FROM instantLocation_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM instantLocation_table ORDER BY creationInstant DESC")
    fun getData(): LiveData<List<BaseLocation>>

    @Query("SELECT * FROM instantLocation_table WHERE creationInstant BETWEEN :startTime AND :endTime")
    fun getRoute(startTime: Long, endTime: Long): LiveData<List<BaseLocation>>
}