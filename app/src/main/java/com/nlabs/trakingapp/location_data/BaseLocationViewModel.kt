package com.nlabs.trakingapp.location_data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseLocationViewModel(application: Application): AndroidViewModel(application) {

    val getData: LiveData<List<BaseLocation>>
    private val repository: BaseLocationRepository

    init {
        val locationDao = BaseLocationDatabase.getDatabase(application).dao()
        repository = BaseLocationRepository(locationDao)
        getData = repository.getData
    }

    fun upsertLocation(baseLocation: BaseLocation){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLocation(baseLocation)
        }
    }

    fun getRoute(startTime: Long, endTime: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRoute(startTime, endTime)
        }
    }

    fun deleteLocation(baseLocation: BaseLocation){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLocation(baseLocation)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllData()
        }
    }
}