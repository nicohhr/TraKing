package com.nlabs.trakingapp.location_data

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
        getData = repository.getData
    }

    fun upsertLocation(instantLocation: InstantLocation){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLocation(instantLocation)
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