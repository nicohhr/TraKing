package com.nlabs.trakingapp.data.location

import androidx.lifecycle.LiveData

class InstantLocationRepository (private val instantLocationDao: InstantLocationDao) {

    val getData: LiveData<List<InstantLocation>> = instantLocationDao.getData()

    suspend fun getRoute(startTime: Long , endTime: Long){
        instantLocationDao.getRoute(startTime, endTime)
    }

    suspend fun addLocation(instantLocation: InstantLocation){
        instantLocationDao.upsertLocation(instantLocation)
    }

    suspend fun deleteLocation(instantLocation: InstantLocation){
        instantLocationDao.deleteLocation(instantLocation)
    }

    suspend fun deleteAllData(){
        instantLocationDao.deleteAllData()
    }

}