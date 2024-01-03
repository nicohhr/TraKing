package com.nlabs.trakingapp.data.route

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.nlabs.trakingapp.data.location.InstantLocation

@Dao
interface RouteDao {

    @Upsert
    suspend fun upsertRoute(route: Route)

    @Delete
    suspend fun deleteRouteInstance(route: Route)

    @Query("DELETE FROM routes_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM routes_table ORDER BY startTime DESC")
    fun getRoutesData(): LiveData<List<Route>>

}