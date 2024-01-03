package com.nlabs.trakingapp.data.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InstantLocationViewModel(application: Application): AndroidViewModel(application) {

    val getData: LiveData<List<InstantLocation>>
    private val repository: InstantLocationRepository

    init {
        val locationDao = InstantLocationDatabase.getDatabase(application).dao()
        repository = InstantLocationRepository(locationDao)
        getData = repository.getLocationData
    }

    fun upsertLocation(instantLocation: InstantLocation){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLocation(instantLocation)
        }
    }

    fun getRoute(startTime: Long, endTime: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRoute(startTime, endTime)
        }
    }

    fun deleteLocation(instantLocation: InstantLocation){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLocation(instantLocation)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllData()
        }
    }
}