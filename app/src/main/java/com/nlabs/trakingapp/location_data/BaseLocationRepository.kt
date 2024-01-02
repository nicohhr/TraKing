package com.nlabs.trakingapp.location_data

import androidx.lifecycle.LiveData

class BaseLocationRepository (private val baseLocationDao: BaseLocationDao) {

    val getData: LiveData<List<BaseLocation>> = baseLocationDao.getData()

    suspend fun getRoute(startTime: Long , endTime: Long){
        baseLocationDao.getRoute(startTime, endTime)
    }

    suspend fun addLocation(baseLocation: BaseLocation){
        baseLocationDao.upsertLocation(baseLocation)
    }

    suspend fun deleteLocation(baseLocation: BaseLocation){
        baseLocationDao.deleteLocation(baseLocation)
    }

    suspend fun deleteAllData(){
        baseLocationDao.deleteAllData()
    }

}