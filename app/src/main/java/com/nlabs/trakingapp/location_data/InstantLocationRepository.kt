package com.nlabs.trakingapp.location_data

import androidx.lifecycle.LiveData

class InstantLocationRepository (private val instantLocationDao: InstantLocationDao) {

    val getData: LiveData<List<InstantLocation>> = instantLocationDao.getData()

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